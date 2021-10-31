package core;

import models.ClientMove;
import ports.ClientMover;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Policy;

public class TestClient {
    public static void main(String[] args) throws RemoteException, NotBoundException {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        Registry registry = LocateRegistry.getRegistry(9876);
        ClientMover clientMover = new ClientMoverImpl();
        ClientMover clientMoverStub = (ClientMover) UnicastRemoteObject.exportObject(clientMover, 9872);

        registry.rebind("Client 2", (Remote) clientMoverStub);

        ClientMover clientMover1 = (ClientMover) registry.lookup("Client 1");

        System.out.println(clientMover1.sendMove(new ClientMove()));
    }
}
