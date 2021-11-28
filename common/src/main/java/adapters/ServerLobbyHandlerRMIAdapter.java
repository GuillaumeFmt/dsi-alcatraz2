package adapters;

import exceptions.ServerNotPrimaryException;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import models.Lobby;
import ports.ServerLobbyHandler;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ServerNotActiveException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
public class ServerLobbyHandlerRMIAdapter implements ServerLobbyHandler {

    private Registry registry;
    private final String serverName;
    private ServerLobbyHandlerRMI serverLobbyHandlerProxy;

    public ServerLobbyHandlerRMIAdapter(int serverPort, String serverName) {
        this.serverName = serverName;
        try {
            this.registry = LocateRegistry.getRegistry(serverPort);
            this.serverLobbyHandlerProxy = getServerLobbyHandlerProxy();
        } catch (RemoteException | NotBoundException e) {
            this.registry = null;
            this.serverLobbyHandlerProxy = null;
        }
    }

    @Override
    public UUID register(ClientPlayer clientPlayer) throws ServerNotPrimaryException {
        try {
            return serverLobbyHandlerProxy.register(clientPlayer);
        } catch (RemoteException e) {
            log.info("Got remote exception while trying to register {}", e.getMessage());
            throw new ServerNotPrimaryException();
        }
    }

    @Override
    public List<Lobby> currentLobbies() throws ServerNotPrimaryException {
        try {
            return serverLobbyHandlerProxy.currentLobbies();
        } catch (RemoteException e) {
            e.printStackTrace();
            log.info("Got remote exception while trying to get Lobbies {}", e.getMessage());
            throw new ServerNotPrimaryException();
        }
    }

    @Override
    public UUID createLobby(String lobbyName, ClientPlayer clientPlayer) throws ServerNotPrimaryException {
        try {
            return serverLobbyHandlerProxy.createLobby(lobbyName, clientPlayer);
        } catch (RemoteException e) {
            log.info("Got remote exception while trying to create Lobby {}", e.getMessage());
            throw new ServerNotPrimaryException();
        }
    }

    @Override
    public List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) throws ServerNotPrimaryException {
        try {
            return serverLobbyHandlerProxy.joinLobby(lobby, clientPlayer);
        } catch (RemoteException e) {
            e.printStackTrace();
            log.info("Got remote exception while trying to join lobby {}", e.getMessage());
            throw new ServerNotPrimaryException();
        }
        //return List.of(new ClientPlayer("127.0.0.1", 9871, "Client 1"), new ClientPlayer("127.0.0.1", 9872, "Client 2"));
    }

    @Override
    public Boolean leaveLobby(ClientPlayer clientPlayer) throws ServerNotPrimaryException {
        try {
            return serverLobbyHandlerProxy.leaveLobby(clientPlayer);
        } catch (RemoteException e) {
            log.info("Got remote exception while trying to leave lobby {}", e.getMessage());
            throw new ServerNotPrimaryException();
        }
    }

    @Override
    public Boolean startGame(Lobby lobby) throws ServerNotPrimaryException {
        try {
            return serverLobbyHandlerProxy.startGame(lobby);
        } catch (RemoteException e) {
            log.info("Got remote exception while trying to start game {}", e.getMessage());
            throw new ServerNotPrimaryException();
        }
    }

    private ServerLobbyHandlerRMI getServerLobbyHandlerProxy() throws NotBoundException, RemoteException {
        return (ServerLobbyHandlerRMI)  registry.lookup(serverName);
    }
}
