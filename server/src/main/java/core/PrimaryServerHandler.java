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
        SpreadGroup myGroup = SpreadGroupState.myGroup;

        if (groupMembers.length == 1) {
            this.actualPrimaryServerName = groupMembers[0].toString();
            System.out.println("I am first so I am the primary");
        } else if (groupMembers.length == 2) {
            for (SpreadGroup groupMember : groupMembers) {
                if (!groupMember.equals(myGroup)) {
                    this.actualPrimaryServerName = groupMember.toString();
                    System.out.printf("Found %s to be primary server\n", this.actualPrimaryServerName);
                }
            }
        } else {
            for (SpreadGroup groupMember : groupMembers) {
                if (!groupMember.equals(myGroup)) {
                    System.out.println("Asking group to send primary");
                    MessageHandler messageHandler = new MessageHandler();
                    SpreadMessageData messageData = new SpreadMessageData();
                    messageData.setMessageType(MessageType.SEND_PRIMARY);
                    messageHandler.sendMessage(messageData);
                    break;
                }
            }
        }
    }

    public void primaryServerElection(SpreadGroup[] groupMembers) {
        //TODO: Überarbeiten
        int biggest = 0;
        SpreadGroup temporaryPrimaryServer = null;
        for (SpreadGroup groupMember : groupMembers) {
            int groupMemberStringLength = groupMember.toString().length();
            int groupMemberId = Integer.getInteger(groupMember.toString().substring((groupMemberStringLength - 2), groupMemberStringLength));
            if (biggest < groupMemberId) {
                biggest = groupMemberId;
                temporaryPrimaryServer = groupMember;
            }
        }
        this.actualPrimaryServerName = temporaryPrimaryServer.toString();
    }

    public void handleSendPrimaryMessage(SpreadGroup sender) {
        System.out.println(amIPrimary() ? "I am primary" : "I am not primary");
        if (amIPrimary()){
            MessageHandler messageHandler = new MessageHandler();
            SpreadMessageData data = new SpreadMessageData();
            //debug - change back to PRIMARY !
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

    // TODO: check if this can be deleted -> same method in LocalServerState
    public Boolean amIPrimary() {
        if (actualPrimaryServerName != null) {
            SpreadGroup myGroup = SpreadGroupState.myGroup;
            return myGroup.toString().equals(actualPrimaryServerName);
        } else {
            return false;
        }
    }

    public void setPrimary(String primary) {
        this.actualPrimaryServerName = primary;
        LocalServerState.getInstance().setPrimary(primary);
    }
}
