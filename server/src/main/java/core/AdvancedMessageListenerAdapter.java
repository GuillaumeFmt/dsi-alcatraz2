package core;

import spread.AdvancedMessageListener;
import spread.SpreadMessage;

public class AdvancedMessageListenerAdapter implements AdvancedMessageListener {

    @Override
    public void regularMessageReceived(SpreadMessage spreadMessage) {
        System.out.println("Regular message: " + new String(spreadMessage.getData()));
    }

    @Override
    public void membershipMessageReceived(SpreadMessage spreadMessage) {
        System.out.println("Membership message: " + new String(spreadMessage.getData()));
    }
}
