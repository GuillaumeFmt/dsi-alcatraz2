package adapters.out;

import models.ClientMove;
import models.ClientPlayer;
import ports.in.RemoteMoveReceiver;

import java.rmi.RemoteException;

public class ClientMoverRMIStub implements ClientMoverRMI {

    private final RemoteMoveReceiver remoteMoveReceiver;

    public ClientMoverRMIStub(RemoteMoveReceiver remoteMoveReceiver) {
        this.remoteMoveReceiver = remoteMoveReceiver;
    }

    @Override
    public boolean sendMove(ClientMove clientMove) throws RemoteException {
        System.out.println("received a move - " + clientMove.getPlayer().getId()); // TODO just for demonstration -> remove
        remoteMoveReceiver.updateLocalGameState(clientMove);
        return remoteMoveReceiver.sendAcknowledge();
    }

    @Override
    public boolean nextPlayerMove(ClientPlayer clientPlayer) throws RemoteException {
        return false;
    }
}
