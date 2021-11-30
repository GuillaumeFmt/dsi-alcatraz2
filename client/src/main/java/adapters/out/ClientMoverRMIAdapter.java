package adapters.out;

import exceptions.ClientNotReachableException;
import models.ClientMove;
import models.ClientPlayer;
import ports.out.ClientMover;
import utils.RetryOnExceptionHandler;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientMoverRMIAdapter implements ClientMover {

    private Registry registry;
    private final String remoteName;
    private ClientMoverRMI clientMoverProxy;
    private final RetryOnExceptionHandler retryOnExceptionHandler;

    public ClientMoverRMIAdapter(int serverPort, String remoteName, String serverHost) {
        retryOnExceptionHandler = new RetryOnExceptionHandler(3, 10);
        this.remoteName = remoteName;
        try {
            this.registry = LocateRegistry.getRegistry(serverHost, serverPort);
            this.clientMoverProxy = getClientMoverProxy();
        } catch (RemoteException | NotBoundException e) {
            this.registry = null;
            this.clientMoverProxy = null;
        }
    }

    @Override
    public boolean sendMove(ClientMove clientMove) throws ClientNotReachableException {
        while(true) {
            try {
                return clientMoverProxy.sendMove(clientMove);
            } catch (RemoteException e) {
                retryOnExceptionHandler.exceptionOccurred();
            }
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
