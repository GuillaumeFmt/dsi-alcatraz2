package core.adapters.out;

import adapters.ServerLobbyHandlerRMI;
import exceptions.ClientNotReachableException;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ServerLobbyHandlerRMIAdapter implements ServerLobbyHandler {

    private final String serverName;
    private Registry registry;
    private final ArrayList<RegistrationServer> servers;
    private ServerLobbyHandlerRMI serverLobbyHandlerProxy;
    private final RetryOnExceptionHandler retryOnExceptionHandler;
    private final Iterator<RegistrationServer> serverPointer;

    public ServerLobbyHandlerRMIAdapter(String serverName, ArrayList<RegistrationServer> servers) {
        this.retryOnExceptionHandler = new RetryOnExceptionHandler(3, 2000);
        this.servers = servers;
        this.serverName = serverName;
        this.serverPointer = servers.iterator();
        reInit();
    }

    private void init() throws ClientNotReachableException {
        if (this.serverPointer.hasNext()) {
            RegistrationServer registrationServer = this.serverPointer.next();
            log.info("Trying to connect to {} on port {}", registrationServer.getHostname(), registrationServer.getPort());
            try {
                this.registry = LocateRegistry.getRegistry(registrationServer.getHostname(), registrationServer.getPort());
                this.serverLobbyHandlerProxy = getServerLobbyHandlerProxy();
            } catch (RemoteException | NotBoundException e) {
                log.error("Caught exception '{}' while trying to get remote object ServerLobbyHandler", e.toString());
                this.registry = null;
                this.serverLobbyHandlerProxy = null;
                this.retryOnExceptionHandler.exceptionOccurred();
                reInit();
            }
        }
    }

    private void reInit(){
        try {
            init();
        } catch (ClientNotReachableException e) {
            log.error("{}", e.getMessage());
            System.exit(1);
        }
    }

    @Override
    public UUID register(ClientPlayer clientPlayer) throws ClientNotReachableException {
        while (true) {
            try {
                return serverLobbyHandlerProxy.register(clientPlayer);
            } catch (RemoteException e) {
                log.info("Retrying another server");
                this.retryOnExceptionHandler.exceptionOccurred();
                reInit();
                continue;
            }
        }
    }

    @Override
    public UUID createLobby(String lobbyName, ClientPlayer clientPlayer) throws ClientNotReachableException {
        while (true) {
            try {
                return serverLobbyHandlerProxy.createLobby(lobbyName, clientPlayer);
            } catch (RemoteException e) {
                log.info("Got remote exception while trying to create Lobby {}", e.getMessage());
                this.retryOnExceptionHandler.exceptionOccurred();
                reInit();
            }
        }
    }

    @Override
    public List<Lobby> currentLobbies() throws ClientNotReachableException {
        while (true) {
            try {
                return serverLobbyHandlerProxy.currentLobbies();
            } catch (RemoteException e) {
                e.printStackTrace();
                log.info("Got remote exception while trying to get Lobbies {}", e.getMessage());
                this.retryOnExceptionHandler.exceptionOccurred();
                reInit();
            }
        }
    }


    @Override
    public List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) throws ClientNotReachableException {
        while (true) {
            try {
                return serverLobbyHandlerProxy.joinLobby(lobby, clientPlayer);
            } catch (RemoteException e) {
                e.printStackTrace();
                log.info("Got remote exception while trying to join lobby {}", e.getMessage());
                this.retryOnExceptionHandler.exceptionOccurred();
                reInit();
            }
        }
        //return List.of(new ClientPlayer("127.0.0.1", 9871, "Client 1"), new ClientPlayer("127.0.0.1", 9872, "Client 2"));
    }

    @Override
    public Boolean leaveLobby(ClientPlayer clientPlayer) throws ClientNotReachableException {
        while (true) {
            try {
                return serverLobbyHandlerProxy.leaveLobby(clientPlayer);
            } catch (RemoteException e) {
                log.info("Got remote exception while trying to leave lobby {}", e.getMessage());
                this.retryOnExceptionHandler.exceptionOccurred();
                reInit();
            }
        }

    }

    @Override
    public Boolean startGame(Lobby lobby) throws ClientNotReachableException {
        try {
            return serverLobbyHandlerProxy.startGame(lobby);
        } catch (RemoteException e) {
            log.info("Got remote exception while trying to start game {}", e.getMessage());
            this.retryOnExceptionHandler.exceptionOccurred();
            reInit();
        }
        return false;
    }

    private ServerLobbyHandlerRMI getServerLobbyHandlerProxy() throws NotBoundException, RemoteException {
        return (ServerLobbyHandlerRMI) registry.lookup(serverName);
    }


}
