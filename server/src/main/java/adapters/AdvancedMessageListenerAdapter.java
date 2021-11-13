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
        System.out.println("Received Membership Message");
        MembershipInfo membershipInfo = spreadMessage.getMembershipInfo();
        if(membershipInfo.isRegularMembership()){
            System.out.println("Group: " + membershipInfo.getGroup().toString());
            System.out.println("GroupID: " + membershipInfo.getGroupID().toString());
            System.out.println("GroupMembers: ");
            Arrays.stream(membershipInfo.getMembers()).forEach(spreadGroup -> System.out.println(spreadGroup.toString()));
        }
    }
}
