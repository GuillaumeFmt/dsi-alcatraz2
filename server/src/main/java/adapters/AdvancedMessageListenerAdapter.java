package adapters;

import spread.AdvancedMessageListener;
import spread.MembershipInfo;
import spread.SpreadGroup;
import spread.SpreadMessage;

import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdvancedMessageListenerAdapter implements AdvancedMessageListener {

    @Override
    public void regularMessageReceived(SpreadMessage spreadMessage) {
        System.out.println("Regular message: " + new String(spreadMessage.getData()));
    }

    @Override
    public void membershipMessageReceived(SpreadMessage spreadMessage) {
        MembershipInfo membershipInfo = spreadMessage.getMembershipInfo();

        System.out.print("Received Spread Membership Message due to ");
        if (membershipInfo.isCausedByJoin()) {
            System.out.println("the JOIN of " + membershipInfo.getJoined());
        } else if (membershipInfo.isCausedByDisconnect()) {
            System.out.println("the DISCONNECT of " + membershipInfo.getDisconnected());
        } else if (membershipInfo.isCausedByLeave()) {
            System.out.println("LEAVE of " + membershipInfo.getLeft());
        } else if (membershipInfo.isCausedByNetwork()) {
            System.out.println("a NETWORK change.");
        } else if (membershipInfo.isTransition()) {
            System.out.println("TRANSITIONAL membership for group " + membershipInfo.getGroup());
        } else if (membershipInfo.isSelfLeave()) {
            System.out.println("SELF-LEAVE message for group " + membershipInfo.getGroup());
        }

        if(membershipInfo.isRegularMembership()){
            System.out.println("Group: " + membershipInfo.getGroup().toString());
            System.out.println("GroupID: " + membershipInfo.getGroupID().toString());
            System.out.println("Number of members in group: " + Arrays.stream(membershipInfo.getMembers()).count());
            System.out.println("GroupMembers: ");
            Arrays.stream(membershipInfo.getMembers()).forEach(spreadGroup -> System.out.println("  " + spreadGroup.toString()));
        }
    }
}
