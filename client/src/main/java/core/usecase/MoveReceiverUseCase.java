package core.usecase;

import models.ClientMove;
import ports.out.ClientMover;
import ports.in.MoveReceiver;

public class MoveReceiverUseCase implements MoveReceiver {

    private ClientMover clientMover;

    public MoveReceiverUseCase(ClientMover clientMover) {
        this.clientMover = clientMover;
    }

    @Override
    public void updateLocalMove(ClientMove clientMove) {
        // TODO
    }

    @Override
    public void moveDoneMessage(int playerId) {
        System.out.println("Move done! " + playerId);
        clientMover.sendMove(new ClientMove()); // TODO get the real local clientMove
    }
}
