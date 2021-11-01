package ports.in;

import models.ClientMove;

public interface MoveReceiver {

    public void updateLocalMove(ClientMove clientMove);
}
