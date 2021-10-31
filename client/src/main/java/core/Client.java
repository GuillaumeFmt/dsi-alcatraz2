package core;

import ports.ClientMover;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Policy;

public class Client {
    public static void main(String[] args) throws RemoteException {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        Registry registry = LocateRegistry.getRegistry(9876);
        ClientMover clientMover = new ClientMoverImpl();
        ClientMover clientMoverStub = (ClientMover) UnicastRemoteObject.exportObject(clientMover, 9871);

        registry.rebind("Client 1", clientMoverStub);

        while (true) {

        }
    }
}
