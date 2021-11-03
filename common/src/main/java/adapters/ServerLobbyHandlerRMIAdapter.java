package adapters;

import adapters.out.ClientMoverRMI;
import adapters.out.ClientMoverRMIStub;
import at.falb.games.alcatraz.api.Player;
import models.ClientPlayer;
import models.Lobby;
import ports.ServerLobbyHandler;
import ports.in.ClientAcknowledge;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ServerLobbyHandlerRMIAdapter implements ServerLobbyHandler {

    private Registry registry;
    private final String clientName;
    private final String remoteName;
    private final int clientPort;

    public ServerLobbyHandlerRMIAdapter(int serverPort, String clientName, String remoteName, int clientPort) {
        this.clientName = clientName;
        this.remoteName = remoteName; // TODO remove?
        this.clientPort = clientPort;
        try {
            this.registry = LocateRegistry.getRegistry(serverPort);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registerClientMoverStub(ClientAcknowledge clientAcknowledge) {
        try {
            ClientMoverRMI clientMoverRMIStub = (ClientMoverRMI) UnicastRemoteObject.exportObject(new ClientMoverRMIStub(clientAcknowledge), clientPort);
            registry.rebind(clientName, clientMoverRMIStub);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ClientMoverRMI getClientMoverProxy() {
        try {
            return (ClientMoverRMI) registry.lookup(remoteName);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public UUID register(ClientPlayer clientPlayer) {
        return null;
    }

    @Override
    public List<Lobby> currentLobbies() {
        return Collections.emptyList();
    }

    @Override
    public Lobby createLobby(String lobbyName, ClientPlayer clientPlayer) {
        return null;
    }

    @Override
    public List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) {
        return List.of(new ClientPlayer("127.0.01", 9871, new Player(1)), new ClientPlayer("127.0.01", 9872, new Player(1)));
    }

    @Override
    public Boolean leaveLobby(ClientPlayer clientPlayer) {
        return false;
    }

    @Override
    public Boolean startGame(Lobby lobby) {
        return false;
    }
}
