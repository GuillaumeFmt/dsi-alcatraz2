package core;

import model.LocalServerState;
import model.MessageType;
import model.SpreadGroupState;
import model.SpreadMessageData;
import spread.SpreadException;
import spread.SpreadGroup;


public class PrimaryServerHandler {

    private static PrimaryServerHandler primaryServerHandlerInstance;
    public String actualPrimaryServerName;

    public static PrimaryServerHandler getInstance() {
        if (primaryServerHandlerInstance == null) {
            primaryServerHandlerInstance = new PrimaryServerHandler();
        }
        return primaryServerHandlerInstance;
    }

    public void setPrimary(SpreadGroup[] groupMembers) {
        String myGroup = LocalServerState.getInstance().getMyServerName();

        if (groupMembers.length == 1) {
            this.actualPrimaryServerName = groupMembers[0].toString();
            System.out.println("I am first so I am the primary");
        } else if (groupMembers.length == 2) {
            for (SpreadGroup groupMember : groupMembers) {
                if (!groupMember.toString().equals(myGroup)) {
                    this.actualPrimaryServerName = groupMember.toString();
                    System.out.printf("Found %s to be primary server\n", this.actualPrimaryServerName);
                }
            }
        } else {
            for (SpreadGroup groupMember : groupMembers) {
                if (!groupMember.toString().equals(myGroup)) {
                    System.out.println("Asking group to send primary");
                    MessageHandler messageHandler = new MessageHandler();
                    SpreadMessageData messageData = new SpreadMessageData();
                    messageData.setMessageType(MessageType.SEND_PRIMARY);
                    messageHandler.sendMessage(messageData);
                    break;
                }
            }
        }
        LocalServerState.getInstance().setPrimary(this.actualPrimaryServerName);
    }

    public void handleSendPrimaryMessage(SpreadGroup sender) {
        System.out.println(LocalServerState.getInstance().amIPrimary() ? "I am primary" : "I am not primary");
        if (LocalServerState.getInstance().amIPrimary()){
            MessageHandler messageHandler = new MessageHandler();
            SpreadMessageData data = new SpreadMessageData();
            data.setMessageType(MessageType.PRIMARY);
            data.setPrimary(this.actualPrimaryServerName);
            System.out.printf("Trying to send message with type PRIMARY to %s\n", sender.toString());
            try {
                messageHandler.sendMessage(data, sender);
            } catch (SpreadException e) {
                e.printStackTrace();
            }
        }
    }

    public void setPrimary(String primary) {
        this.actualPrimaryServerName = primary;
        LocalServerState.getInstance().setPrimary(primary);
    }
}
