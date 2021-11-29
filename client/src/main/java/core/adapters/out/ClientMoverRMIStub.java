package core.adapters.out;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.ClientMove;
import models.ClientPlayer;
import ports.in.RemoteMoveReceiver;

import java.rmi.RemoteException;

@Slf4j
@RequiredArgsConstructor
public class ClientMoverRMIStub implements ClientMoverRMI {

    private final RemoteMoveReceiver remoteMoveReceiver;

    @Override
    public boolean sendMove(ClientMove clientMove) throws RemoteException {
        log.info("received a move: {}", clientMove);
        remoteMoveReceiver.updateLocalGameState(clientMove);
        return remoteMoveReceiver.sendAcknowledge();
    }

    @Override
    public boolean nextPlayerMove(ClientPlayer clientPlayer) throws RemoteException {
        return false;
    }
}
