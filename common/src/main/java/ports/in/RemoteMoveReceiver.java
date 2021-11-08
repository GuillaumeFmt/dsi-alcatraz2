package ports.in;

import models.ClientMove;

public interface RemoteMoveReceiver {
    boolean sendAcknowledge();

    void updateLocalGameState(ClientMove clientMove);
}
