package adapters.out;

import models.ClientMove;
import models.ClientPlayer;
import ports.in.RemoteMoveReceiver;
import ports.out.ClientMover;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientMoverRMIAdapter implements ClientMover {

    private Registry registry;
    private final String clientName;
    private final String remoteName;
    private final int clientPort;
    private ClientMoverRMI clientMoverProxy;

    public ClientMoverRMIAdapter(int serverPort, String clientName, int clientPort, String remoteName, RemoteMoveReceiver remoteMoveReceiver) {
        this.clientName = clientName;
        this.clientPort = clientPort;
        this.remoteName = remoteName;
        try {
            this.registry = LocateRegistry.getRegistry(serverPort);
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

    private ClientMoverRMI getClientMoverProxy() throws NotBoundException, RemoteException {
        return (ClientMoverRMI) registry.lookup(remoteName);
    }
}
