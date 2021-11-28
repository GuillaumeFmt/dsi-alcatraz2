package adapters;

import models.ClientPlayer;
import models.Lobby;
import ports.ServerLobbyHandler;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

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
    public UUID register(ClientPlayer clientPlayer) {
        try {
            return serverLobbyHandlerProxy.register(clientPlayer);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Lobby> currentLobbies() {
        try {
            return serverLobbyHandlerProxy.currentLobbies();
        } catch (RemoteException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public UUID createLobby(String lobbyName, ClientPlayer clientPlayer) {
        // TODO: implement logic to send the request again if the current connected server is not the primary
        try {
            return serverLobbyHandlerProxy.createLobby(lobbyName, clientPlayer);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) {
        try {
            return serverLobbyHandlerProxy.joinLobby(lobby, clientPlayer);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
        //return List.of(new ClientPlayer("127.0.0.1", 9871, "Client 1"), new ClientPlayer("127.0.0.1", 9872, "Client 2"));
    }

    @Override
    public Boolean leaveLobby(ClientPlayer clientPlayer) {
        try {
            return serverLobbyHandlerProxy.leaveLobby(clientPlayer);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean startGame(Lobby lobby) {
        try {
            return  serverLobbyHandlerProxy.startGame(lobby);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ServerLobbyHandlerRMI getServerLobbyHandlerProxy() throws NotBoundException, RemoteException {
        return (ServerLobbyHandlerRMI)  registry.lookup(serverName);
    }
}
