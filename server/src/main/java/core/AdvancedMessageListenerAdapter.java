package core;

import spread.AdvancedMessageListener;
import spread.MembershipInfo;
import spread.SpreadGroup;
import spread.SpreadMessage;

import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AdvancedMessageListenerAdapter implements AdvancedMessageListener {

    private static Logger logger = Logger.getLogger(AdvancedMessageListenerAdapter.class.getName());

    @Override
    public void regularMessageReceived(SpreadMessage spreadMessage) {
        info("Regular message: " + new String(spreadMessage.getData()));
    }

    @Override
    public void membershipMessageReceived(SpreadMessage spreadMessage) {
        info("Received Membership Message");
        MembershipInfo membershipInfo = spreadMessage.getMembershipInfo();
        if(membershipInfo.isRegularMembership()){
            info("Group: " + membershipInfo.getGroup().toString());
            info("GroupID: " + membershipInfo.getGroupID().toString());
            info("GroupMembers: ");
            Arrays.stream(membershipInfo.getMembers()).forEach(spreadGroup -> info(spreadMessage.toString()));
        }
    }
    private void info(String message){
        logger.log(Level.INFO, message);
    }
}
