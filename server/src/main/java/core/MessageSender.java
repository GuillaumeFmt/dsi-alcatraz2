package core;

import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadMessage;

public class MessageSender {

    SpreadConnectionManager spreadConnectionManager = SpreadConnectionManager.getInstance();

    public void reliableMulticast(String testData) {
        SpreadConnection spreadConnection = spreadConnectionManager.getSpreadConnection();
        SpreadMessage message = new SpreadMessage();
        message.setData(testData.getBytes());
        message.addGroup("AlcatrazGroup");
        message.setReliable();
        try {
            spreadConnection.multicast(message);
        } catch (SpreadException e) {
            e.printStackTrace();
        }
    }
}
