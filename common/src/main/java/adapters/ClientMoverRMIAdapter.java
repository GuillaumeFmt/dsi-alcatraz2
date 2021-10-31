package adapters;

import models.ClientMove;
import models.ClientPlayer;
import ports.ClientMover;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ClientMoverRMIAdapter implements ClientMover {

    private Registry registry;
    private final String clientName;
    private final int clientPort;
    private final ClientMoverRMI clientMoverStub;
    private ClientMoverRMI clientMoverProxy;

    public ClientMoverRMIAdapter(int serverPort, String clientName, int clientPort) {
        this.clientName = clientName;
        this.clientPort = clientPort;

        this.clientMoverStub = new ClientMoverRMIStub();
        try {
            this.registry = LocateRegistry.getRegistry(serverPort);
            registerClientMoverStub();
            this.clientMoverProxy = getClientMoverProxy();
        } catch (RemoteException | NotBoundException e) {
            this.registry = null;
            this.clientMoverProxy = null;
        }
    }

    @Override
    public boolean sendMove(ClientMove clientMove) {
        try {
            return clientMoverProxy.sendMove(clientMove);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean nextPlayerMove(ClientPlayer clientPlayer) {
        try {
            return clientMoverProxy.nextPlayerMove(clientPlayer);
        } catch (RemoteException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void registerClientMoverStub() throws RemoteException {
        ClientMoverRMI clientMoverRMIStub = (ClientMoverRMI) UnicastRemoteObject.exportObject(this.clientMoverStub, this.clientPort);
        registry.rebind(clientName, clientMoverRMIStub);
    }

    private ClientMoverRMI getClientMoverProxy() throws NotBoundException, RemoteException {
        return (ClientMoverRMI) registry.lookup(this.clientName);
    }
}
