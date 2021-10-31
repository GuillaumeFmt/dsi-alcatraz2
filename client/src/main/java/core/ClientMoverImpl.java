package core;

import models.ClientMove;
import models.ClientPlayer;
import ports.ClientMover;

public class ClientMoverImpl implements ClientMover {
    public boolean sendMove(ClientMove clientMove) {
        return false;
    }

    public boolean nextPlayerMove(ClientPlayer clientPlayer) {
        return true;
    }
}
