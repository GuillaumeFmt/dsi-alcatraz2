package core;

import security.AlcatrazSecurityPolicy;
import spread.SpreadConnection;
import spread.SpreadException;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.Policy;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {
    public static void main(String[] args) throws RemoteException, UnknownHostException {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        Registry registry = LocateRegistry.createRegistry(9876);

        /*
        * this list defines the host to ip mapping where the spread daemon is running
        * */
        HashMap<String, String> hosts = new HashMap<>();
        hosts.put("dsiars01", "10.2.1.4");
        hosts.put("dsiars02", "10.2.2.4");
        hosts.put("dsiars03", "10.2.3.4");
        hosts.put("MBP-von-Nichil", "192.168.178.52");

        String hostname = InetAddress.getLocalHost().getHostName();

        SpreadConnection spreadConnection = new SpreadConnection();
        try {
            spreadConnection.connect(InetAddress.getByName(hosts.get(hostname)), 4803, hostname, false, true);
        } catch (SpreadException e) {
            //TODO do something here
            e.printStackTrace();
        }

        System.out.println(spreadConnection.getPrivateGroup());
        System.out.println("Spread connection initialised with local daemon");

        System.out.println("Init done. Starting server loop");
        while (true) {

        }
    }

}
