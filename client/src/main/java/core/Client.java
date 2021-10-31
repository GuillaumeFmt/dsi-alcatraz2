package core;

import adapters.ClientMoverRMIAdapter;
import ports.ClientMover;
import security.AlcatrazSecurityPolicy;

import java.rmi.RemoteException;
import java.security.Policy;

public class Client {
    public static void main(String[] args) {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        ClientMover clientMover = new ClientMoverRMIAdapter(9876, "Client 1", 9871);

        while (true) {

        }
    }
}
