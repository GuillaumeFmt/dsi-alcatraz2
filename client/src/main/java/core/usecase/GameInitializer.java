package core.usecase;

import adapters.out.ClientMoverRMI;
import adapters.out.ClientMoverRMIStub;
import core.Client;
import models.ClientPlayer;
import models.Lobby;
import ports.ServerLobbyHandler;
import ports.in.RemoteMoveReceiver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.UUID;

public class GameInitializer {

    private final ServerLobbyHandler serverLobbyHandler;
    private final String clientName;
    private final int clientPort;
    private Registry registry;

    public GameInitializer(int serverPort, ServerLobbyHandler serverLobbyHandler, String clientName, int clientPort) {
        this.serverLobbyHandler = serverLobbyHandler;
        this.clientName = clientName;
        this.clientPort = clientPort;
        try {
            this.registry = LocateRegistry.getRegistry(serverPort);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void init() {
        registerClientMoverStub(new RemoteMoveReceiverUseCase());
        UUID id = serverLobbyHandler.register(new ClientPlayer("0.0.0.0", clientPort, clientName));
        System.out.println("My Player UUID: " + id.toString());
    }

    public void createLobby(String lobbyName) {
        UUID id = serverLobbyHandler.createLobby(lobbyName, new ClientPlayer("0.0.0.0", clientPort, clientName));
        System.out.println("My Lobby UUID: " + id.toString());
    }

    public List<Lobby> getCurrentLobbies() {
        return serverLobbyHandler.currentLobbies();
    }

    public void joinLobby(Lobby lobby, ClientPlayer clientPlayer) {
        List<ClientPlayer> currentClientPlayerInLobby = serverLobbyHandler.joinLobby(lobby, clientPlayer);
        if (!currentClientPlayerInLobby.isEmpty())
            System.out.println("Lobby joined!!!");
        currentClientPlayerInLobby.forEach(player -> System.out.println("Player in Lobby: " + player.getPlayerName()));
    }

    public void leaveLobby(ClientPlayer clientPlayer) {
        boolean leaved = serverLobbyHandler.leaveLobby(clientPlayer);
        if (leaved)
            System.out.println("You leaved your current lobby!");
    }

    private void registerClientMoverStub(RemoteMoveReceiver remoteMoveReceiver) {
        try {
            ClientMoverRMI clientMoverRMIStub = (ClientMoverRMI) UnicastRemoteObject.exportObject(new ClientMoverRMIStub(remoteMoveReceiver), clientPort);
            registry.rebind(clientName, clientMoverRMIStub);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
