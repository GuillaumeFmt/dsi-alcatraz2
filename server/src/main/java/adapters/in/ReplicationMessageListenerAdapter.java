package adapters.in;

import core.MessageHandler;
import core.PrimaryServerHandler;
import model.LocalServerState;
import model.SpreadMessageData;
import spread.BasicMessageListener;
import spread.SpreadMessage;

import java.io.IOException;

// TODO: remove this class! LEICHE

public class ReplicationMessageListenerAdapter implements BasicMessageListener {

    @Override
    public void messageReceived(SpreadMessage spreadMessage) {
        try {
            SpreadMessageData spreadMessageData = (SpreadMessageData) MessageHandler.deserialize(spreadMessage.getData());

            //TODO: RECEIVE localServerState
            //TODO: UPDATE localServerSate

            if(LocalServerState.getInstance().amIPrimary()){

/*                switch (spreadMessageData.getMessageType()){
                    case RECEIVED_REGISTER:
                        break;
                    default:
                        break;
                }*/

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

}
