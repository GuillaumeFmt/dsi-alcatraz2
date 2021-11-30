package usecase;

import core.*;
import model.LocalServerState;
import model.MessageType;
import model.SpreadMessageData;

public class ReplicationHandlerUseCase {

    static MessageHandler messageHandler = new MessageHandler();

    private ReplicationHandlerUseCase() {
        // defeat instantiation
    }

    public static void replicateLocalState(LocalServerState localServerState) {
        SpreadMessageData spreadMessageData = new SpreadMessageData();
        spreadMessageData.setMessageType(MessageType.UPDATE_SERVER_STATE);
        spreadMessageData.setLocalServerState(localServerState);
        messageHandler.sendMessage(spreadMessageData);
    }

}
