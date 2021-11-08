package core.usecase;

import at.falb.games.alcatraz.api.Player;
import at.falb.games.alcatraz.api.Prisoner;
import models.ClientMove;
import ports.out.ClientMover;
import ports.in.LocalMoveReceiver;

public class LocalMoveReceiverUseCase implements LocalMoveReceiver {

    private ClientMover clientMover;

    public LocalMoveReceiverUseCase(ClientMover clientMover) {
        this.clientMover = clientMover;
    }

    @Override
    public void updateLocalMove(ClientMove clientMove) {
        // TODO
    }

    @Override
    public void moveDoneMessage(Player player, Prisoner prisoner, int i, int i1, int i2) {
        System.out.println("Move done! " + player.getId());
        clientMover.sendMove(new ClientMove(0, player, prisoner, i, i1, i2));
    }
}
