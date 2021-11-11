package core;

import security.AlcatrazSecurityPolicy;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Policy;

public class Server {
    public static void main(String[] args) throws RemoteException {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        Registry registry = LocateRegistry.createRegistry(9876);

        System.out.println("Init done. Starting server loop");
        while (true) {

        }
    }

}
