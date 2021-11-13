package core;

import adapters.AdvancedMessageListenerAdapter;
import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadGroup;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class SpreadConnectionManager {
    private static SpreadConnectionManager spreadConnectionManager;
    private SpreadConnection spreadConnection;

    public SpreadConnectionManager() {
        this.spreadConnection = new SpreadConnection();

        try {
            spreadConnection.connect(null, 0, "connectionName", false, true);
        } catch (SpreadException e) {
            System.err.println(String.format("Exception '%s' received while trying to connect to Spread Daemon on localhost:4803", e.getMessage()));
            throw new RuntimeException();
        }

        System.out.println(String.format("Connection established with spread daemon on default localhost:4803"));
        System.out.println(String.format("My name is %s", spreadConnection.getPrivateGroup()));

        String groupName = "AlcatrazGroup";
        SpreadGroup group = new SpreadGroup();
        System.out.println(String.format("Trying to join group %s", groupName));

        try {
            group.join(spreadConnection, groupName);
        } catch (SpreadException e) {
            System.err.println(String.format("Exception '%' received while trying to join group %s", e.getMessage(), groupName));
            e.printStackTrace();
        }
        spreadConnection.add(new AdvancedMessageListenerAdapter());
    }

    public static SpreadConnectionManager getInstance() {
        if(spreadConnectionManager == null){
            spreadConnectionManager = new SpreadConnectionManager();
        }
        return spreadConnectionManager;
    }

    public SpreadConnection getSpreadConnection() {
        return this.spreadConnection;
    }
}
