package core.usecase;

import adapters.out.ClientMoverRMI;
import adapters.out.ClientMoverRMIStub;
import models.ClientPlayer;
import ports.ServerLobbyHandler;
import ports.in.RemoteMoveReceiver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

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
        serverLobbyHandler.register(new ClientPlayer("0.0.0.0", clientPort, clientName));
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
