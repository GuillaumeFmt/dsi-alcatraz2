package adapters;

import models.ClientMove;
import models.ClientPlayer;

import java.rmi.RemoteException;

public class ClientMoverRMIStub implements ClientMoverRMI {
    @Override
    public boolean sendMove(ClientMove clientMove) throws RemoteException {
        return false;
    }

    @Override
    public boolean nextPlayerMove(ClientPlayer clientPlayer) throws RemoteException {
        return false;
    }
}
