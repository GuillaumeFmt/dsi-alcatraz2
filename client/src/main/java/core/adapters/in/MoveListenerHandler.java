package core.adapters.in;

import at.falb.games.alcatraz.api.MoveListener;
import at.falb.games.alcatraz.api.Player;
import at.falb.games.alcatraz.api.Prisoner;
import models.ClientMove;
import ports.in.LocalMoveReceiver;

public class MoveListenerHandler implements MoveListener {

    private LocalMoveReceiver localMoveReceiver;

    private MoveListenerHandler() {
        // defeat instantiation
    }

    private MoveListenerHandler(LocalMoveReceiver localMoveReceiver) {
        this.localMoveReceiver = localMoveReceiver;
    }

    public static MoveListenerHandler create(LocalMoveReceiver localMoveReceiver) {
        return new MoveListenerHandler(localMoveReceiver);
    }


    @Override
    public void moveDone(Player player, Prisoner prisoner, int rowOrCol, int row, int col) {
        localMoveReceiver.localMoveReceived(player, prisoner, rowOrCol, row, col);
        localMoveReceiver.updateLocalMove(new ClientMove(0, player, prisoner, rowOrCol, row, col));
    }

    @Override
    public void gameWon(Player player) {
        // TODO document why this method is empty
    }
}
