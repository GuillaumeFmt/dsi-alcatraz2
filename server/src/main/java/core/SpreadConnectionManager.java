package core;

import adapters.in.AdvancedMessageListenerAdapter;
import model.LocalServerState;
import model.SpreadGroupState;
import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadGroup;

public class SpreadConnectionManager {

    private static SpreadConnectionManager spreadConnectionManager;
    private SpreadConnection spreadConnection;
    private SpreadGroup group;
    private String groupName;

    private SpreadConnectionManager() {
        this.groupName = "AlcatrazGroup";
        this.spreadConnection = new SpreadConnection();
        this.group = new SpreadGroup();
        init();
        registerListeners();
    }

    private void registerListeners() {
        spreadConnection.add(new AdvancedMessageListenerAdapter());
    }

    public void init() {
        // if inetAddress is null and port 0, client will try to connect to localhost 4803 which is what we want
        try {
            this.spreadConnection.connect(null, 0, "connectionName", false, true);
        } catch (SpreadException e) {
            System.err.printf("Exception '%s' received while trying to connect to Spread Daemon on localhost:4803%n", e.getMessage());
            throw new RuntimeException();
        }
        LocalServerState.getInstance().setMyServerName(spreadConnection.getPrivateGroup().toString());
        System.out.println("Connection established with spread daemon on default localhost:4803");
        System.out.printf("My name is %s%n", spreadConnection.getPrivateGroup());
        System.out.printf("Trying to join group %s%n", this.groupName);
        try {
            this.group.join(this.spreadConnection, this.groupName);
        } catch (SpreadException e) {
            System.err.printf("Exception '%s' received while trying to join group %s%n", e.getMessage(), this.groupName);
            e.printStackTrace();
        }
    }

    public static SpreadConnectionManager getInstance() {
        return spreadConnectionManager;
    }

    public static void instantiateSpreadConnectionManager(){
        if(spreadConnectionManager == null) {
            spreadConnectionManager = new SpreadConnectionManager();
        }
    }

    public SpreadGroup getSpreadGroup() {
        return this.group;
    }

    public SpreadConnection getSpreadConnection() {
        return this.spreadConnection;
    }
}
