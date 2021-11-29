package adapters.in;

import core.*;
import model.LocalServerState;
import model.MessageType;
import model.SpreadGroupState;
import model.SpreadMessageData;
import spread.*;

import java.io.IOException;
import java.util.Arrays;

public class AdvancedMessageListenerAdapter implements AdvancedMessageListener {

    @Override
    public void regularMessageReceived(SpreadMessage spreadMessage) {
        System.out.print("Received regular message ");
        try {
            SpreadMessageData spreadMessageData = (SpreadMessageData) MessageHandler.deserialize(spreadMessage.getData());

            switch (spreadMessageData.getMessageType()) {
                case SEND_PRIMARY:
                    System.out.printf("with MessageType %s from %s\n", MessageType.SEND_PRIMARY, spreadMessage.getSender());
                    PrimaryServerHandler.getInstance().handleSendPrimaryMessage(spreadMessage.getSender());
                    break;
                case PRIMARY:
                    System.out.printf("with MessageType %s containing %s\n", MessageType.PRIMARY,  spreadMessageData.getPrimary());
                    PrimaryServerHandler.getInstance().setPrimary(spreadMessageData.getPrimary());
                    break;
                case UPDATE_SERVER_STATE:
                    System.out.printf("with MessageType %s containing %s\n", MessageType.UPDATE_SERVER_STATE,  spreadMessageData.getLocalServerState());
                    if (!LocalServerState.getInstance().amIPrimary()) {
                        System.out.println("Setting local Server State...");
                        LocalServerState.setLocalServerState(spreadMessageData.getLocalServerState());
                    }
                    break;
                default:
                    System.out.printf("with other payload %s\n", spreadMessageData);
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void membershipMessageReceived(SpreadMessage spreadMessage) {
        System.out.println("Received membership message");
        MembershipInfo membershipInfo = spreadMessage.getMembershipInfo();
        printMembershipInfoDetails(membershipInfo);
    }

    private void printMembershipInfoDetails(MembershipInfo membershipInfo) {
        if (membershipInfo.isCausedByJoin()) {
            System.out.printf("JOIN: %s\n", membershipInfo.getJoined());
        } else if (membershipInfo.isCausedByDisconnect()) {
            System.out.printf("DISCONNECT: %s\n",  membershipInfo.getDisconnected());
        } else if (membershipInfo.isCausedByLeave()) {
            System.out.printf("LEAVE: %s\n", membershipInfo.getLeft());
        } else if (membershipInfo.isCausedByNetwork()) {
            System.out.println("NETWORK changed.\n");
        } else if (membershipInfo.isTransition()) {
            System.out.printf("TRANSITIONAL: %s\n", membershipInfo.getGroup());
        } else if (membershipInfo.isSelfLeave()) {
            System.out.printf("SELF-LEAVE: %s\n", membershipInfo.getGroup());
        }


        if(membershipInfo.isRegularMembership()){
            GroupID oldGroupID = SpreadGroupState.groupID;
            String currentPrimaryServerName = PrimaryServerHandler.getInstance().actualPrimaryServerName;

            SpreadGroupState.groupID = membershipInfo.getGroupID();
            SpreadGroupState.groupName = membershipInfo.getGroup();
            SpreadGroupState.groupMembers = membershipInfo.getMembers();
            SpreadGroupState.groupSize = Arrays.stream(membershipInfo.getMembers()).count();
            SpreadGroupState.print();

            if ((oldGroupID != null) && (currentPrimaryServerName != null)) {
            //if (oldGroupID != null) {
                if (!oldGroupID.equals(membershipInfo.getGroupID())) {
                    System.out.println("Group-View has changed!");
                    Boolean primaryStillInGroup = false;

                    for (SpreadGroup groupMember : membershipInfo.getMembers()) {
                        System.out.println("Checking member: " + groupMember);
                        if (currentPrimaryServerName.equals(groupMember.toString())) {
                            primaryStillInGroup = true;
                            System.out.println("Primary still in group, no election needed");
                            break;
                        }
                    }
                    if (!primaryStillInGroup) { // Primary election process
                        System.out.println("Primary left group -> primary elected needed...");
                        SpreadGroup newPrimary = membershipInfo.getMembers()[0];
                        System.out.println("New Primary: " + newPrimary.toString());
                        PrimaryServerHandler.getInstance().setPrimary(newPrimary.toString());
                    }
                }
            } else {
                // only execute once at server startup
                PrimaryServerHandler.getInstance().setPrimary(membershipInfo.getMembers());
            }
        }
    }
}
