package core;

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


public class Server {
    public static void main(String[] args) throws RemoteException, UnknownHostException  {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        Registry registry = LocateRegistry.createRegistry(9876);

        String hostname = InetAddress.getLocalHost().getHostName();

        SpreadConnection spreadConnection = new SpreadConnection();
        try {
            spreadConnection.connect(InetAddress.getLocalHost(), 4803, hostname, false, true);
        } catch (SpreadException e) {
            //TODO do something here
            e.printStackTrace();
        }

        System.out.println(spreadConnection.getPrivateGroup());
        System.out.println("Spread connection initialised with local daemon");

        SpreadGroup group = new SpreadGroup();
        try {
            group.join(spreadConnection, "AlcatrazGroup");
        } catch (SpreadException e) {
            e.printStackTrace();
        }

        spreadConnection.add(new AdvancedMessageListenerAdapter());
        String testData = "Some Test Data";

        SpreadMessage message = new SpreadMessage();
        message.setData(testData.getBytes());
        message.addGroup("AlcatrazGroup");
        message.setReliable();
        try {
            spreadConnection.multicast(message);
        } catch (SpreadException e) {
            e.printStackTrace();
        }


        System.out.println("Init done. Starting server loop");
        while (true) {
            // for debugging...
        }
    }

}





