package core;

import adapters.AdvancedMessageListenerAdapter;
import ports.ServerLobbyHandler;
import security.AlcatrazSecurityPolicy;
import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadGroup;
import spread.SpreadMessage;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Policy;

/*
* This class needs to stay in this package and may not be renamed because the deployment depends on it and assumes this
* class to be the starting point of the application
* */

public class Server {
    public static void main(String[] args) throws RemoteException, UnknownHostException  {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        ServerInitializer serverInitializer = new ServerInitializer(9876,"Server");
        serverInitializer.init();

        System.out.println("Server started, initializing .. ");
        String testData = "Some Test Data";
        MessageSender messageSender = new MessageSender();
        messageSender.reliableMulticast(testData);

        while (true) {
            // for debugging...
        }
    }

}





