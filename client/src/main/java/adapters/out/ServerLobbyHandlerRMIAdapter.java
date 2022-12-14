package adapters.out;

import adapters.ServerLobbyHandlerRMI;
import exceptions.ClientNotReachableException;
import exceptions.LobbyException;
import exceptions.ServerNotPrimaryException;
import exceptions.StartGameException;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import models.Lobby;
import models.RegistrationServer;
import ports.ServerLobbyHandler;
import utils.RetryOnExceptionHandler;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

@Slf4j
public class ServerLobbyHandlerRMIAdapter implements ServerLobbyHandler {

    private final String serverName;
    private Registry registry;
    private final ArrayList<RegistrationServer> servers;
    private ServerLobbyHandlerRMI serverLobbyHandlerProxy;
    private final RetryOnExceptionHandler retryOnExceptionHandler;
    private Iterator<RegistrationServer> serverPointer;

    public ServerLobbyHandlerRMIAdapter(String serverName, ArrayList<RegistrationServer> servers) {
        this.retryOnExceptionHandler = new RetryOnExceptionHandler(10, 3000);
        this.servers = servers;
        this.serverName = serverName;
        this.serverPointer = servers.iterator();
        try {
            init();
        } catch (ClientNotReachableException e) {
            e.printStackTrace();
        }
    }

    private void init() throws ClientNotReachableException {
        if (!this.serverPointer.hasNext()) {
            this.serverPointer = servers.iterator();
        }

        RegistrationServer registrationServer = this.serverPointer.next();
        log.info("Trying to connect to {} on port {}", registrationServer.getHostname(), registrationServer.getPort());
        try {
            this.registry = LocateRegistry.getRegistry(registrationServer.getHostname(), registrationServer.getPort());
            this.serverLobbyHandlerProxy = getServerLobbyHandlerProxy();
            log.info("Successfully connected" );
        } catch (RemoteException | NotBoundException e) {
            log.error("Caught exception '{}' while trying to get remote object ServerLobbyHandler", e.toString());
            this.registry = null;
            this.serverLobbyHandlerProxy = null;
            reInit();
        }

    }

    private void reInit(){
        try {
            this.retryOnExceptionHandler.exceptionOccurred();
            init();
        } catch (ClientNotReachableException e) {
            log.error("{}", e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public UUID register(ClientPlayer clientPlayer) {
        while (true) {
            try {
                return serverLobbyHandlerProxy.register(clientPlayer);
            } catch (RemoteException | ServerNotPrimaryException e) {
                log.error("Caught exception {} while trying to register player", e.toString());
                e.printStackTrace();
                reInit();
                continue;
            }
        }
    }

    @Override
    public UUID createLobby(String lobbyName, ClientPlayer clientPlayer) {
        while (true) {
            try {
                return serverLobbyHandlerProxy.createLobby(lobbyName, clientPlayer);
            } catch (RemoteException e) {
                log.info("Got remote exception while trying to create Lobby {}", e.getMessage());
                return null;
            } catch (LobbyException e){
                log.error("Caught exception {}", e.toString());
                return null;
            } catch (ServerNotPrimaryException e) {
                log.info("Contacted server was not primary: {}", e.toString());
                reInit();
            }
        }
    }

    @Override
    public List<Lobby> currentLobbies() {
        while (true) {
            try {
            return serverLobbyHandlerProxy.currentLobbies();
            } catch (RemoteException e) {
                e.printStackTrace();
                log.info("Got remote exception while trying to get Lobbies {}", e.getMessage());
                reInit();
            }
        }
    }


    @Override
    public List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) {
        while (true) {
            try {
                return serverLobbyHandlerProxy.joinLobby(lobby, clientPlayer);
            } catch (RemoteException e) {
                e.printStackTrace();
                log.info("Got remote exception while trying to join lobby {}", e.getMessage());
                reInit();
            } catch (LobbyException e) {
                log.error("Caught exception {}", e.toString());
                return Collections.emptyList();
            } catch (ServerNotPrimaryException e) {
                log.info("Contacted server was not primary: {}", e.toString());
                reInit();
            }
        }
    }

    @Override
    public Boolean leaveLobby(String playerName, UUID lobbyId) {
        while (true) {
            try {
                return serverLobbyHandlerProxy.leaveLobby(playerName, lobbyId);
            } catch (RemoteException e) {
                log.info("Got remote exception while trying to leave lobby {}", e.getMessage());
                reInit();
            } catch (ServerNotPrimaryException e) {
                log.info("Contacted server was not primary: {}", e.toString());
                reInit();
            }
        }
    }

    @Override
    public Boolean startGame(UUID lobbyId) throws StartGameException {
        try {
            return serverLobbyHandlerProxy.startGame(lobbyId);
        } catch (RemoteException e) {
            log.info("Got remote exception while trying to start game {}", e.getMessage());
            reInit();
        } catch (LobbyException e) {
            log.error("Could not start game", e.getMessage());
        }
        return false;
    }

    private ServerLobbyHandlerRMI getServerLobbyHandlerProxy() throws NotBoundException, RemoteException {
        return (ServerLobbyHandlerRMI) registry.lookup(serverName);
    }
}
