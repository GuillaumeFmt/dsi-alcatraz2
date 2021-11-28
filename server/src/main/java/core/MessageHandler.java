package core;

import model.SpreadMessageData;
import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadGroup;
import spread.SpreadMessage;

import java.io.*;

public class MessageHandler {

    SpreadConnectionManager spreadConnectionManager = SpreadConnectionManager.getInstance();

    // is used for multicasting
    public void sendMessage(SpreadMessageData message) {
        SpreadGroup spreadGroup = spreadConnectionManager.getSpreadGroup();
        System.out.println("In sendMessage: Sending message to group " + spreadGroup.toString());
        System.out.println("In sendMessage: Message Content " + message.getMessageType() + message.getPrimary());   // TODO: refactor (case if no primary in message... otherwise null will be printed out)
        try {
            sendMessage(message,spreadGroup);
        } catch (SpreadException e) {
            e.printStackTrace();
        }
    }

    // we can use the private Spread group of each member to unicast the message
    public void sendMessage(SpreadMessageData message, SpreadGroup spreadGroup) throws SpreadException {
        SpreadConnection spreadConnection = spreadConnectionManager.getSpreadConnection();
        SpreadMessage spreadMessage = new SpreadMessage();

        try {
            spreadMessage.setData(serialize(message));
        } catch (IOException e) {
            e.printStackTrace();
        }

        spreadMessage.addGroup(spreadGroup);
        spreadMessage.setCausal();

            //System.out.println("In Try block to multicast message");
            spreadConnection.multicast(spreadMessage);
            //System.out.println("In Try block after multicast message");

        System.out.printf("Successfully sent message %s\n", message.getMessageType());
    }

    public static byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

}
