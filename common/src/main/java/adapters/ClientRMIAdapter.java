package adapters;

import models.ClientMove;
import models.ClientPlayer;
import ports.ClientMover;

import java.rmi.RemoteException;

public class ClientRMIAdapter implements ClientMover {

    public boolean sendMove(ClientMove clientMove) {
        return false;
    }

    public boolean nextPlayerMove(ClientPlayer clientPlayer) {
        return false;
    }
}
