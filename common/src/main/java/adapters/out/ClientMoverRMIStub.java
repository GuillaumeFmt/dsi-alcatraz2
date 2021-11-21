package adapters.out;

import models.ClientMove;
import models.ClientPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.in.RemoteMoveReceiver;

import java.rmi.RemoteException;

public class ClientMoverRMIStub implements ClientMoverRMI {
    private static final Logger logger = LoggerFactory.getLogger(ClientMoverRMIStub.class);

    private final RemoteMoveReceiver remoteMoveReceiver;

    public ClientMoverRMIStub(RemoteMoveReceiver remoteMoveReceiver) {
        this.remoteMoveReceiver = remoteMoveReceiver;
    }

    @Override
    public boolean sendMove(ClientMove clientMove) throws RemoteException {
        logger.info("received a move: {}", clientMove);
        remoteMoveReceiver.updateLocalGameState(clientMove);
        return remoteMoveReceiver.sendAcknowledge();
    }

    @Override
    public boolean nextPlayerMove(ClientPlayer clientPlayer) throws RemoteException {
        return false;
    }
}
