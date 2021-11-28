package usecase;

import core.*;
import model.LocalServerState;
import model.MessageType;
import model.SpreadMessageData;
import models.ClientPlayer;
import models.Lobby;

import java.util.List;
import java.util.UUID;

public class ReplicationHandlerUseCase {

    static MessageHandler messageHandler = new MessageHandler();

    static void replicateLocalState(LocalServerState localServerState) {
        SpreadMessageData spreadMessageData = new SpreadMessageData();
        spreadMessageData.setMessageType(MessageType.UPDATE_SERVER_STATE);
        spreadMessageData.setLocalServerState(localServerState);
        messageHandler.sendMessage(spreadMessageData);
    }

}
