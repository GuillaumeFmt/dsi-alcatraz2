package adapters.in;

import at.falb.games.alcatraz.api.MoveListener;
import at.falb.games.alcatraz.api.Player;
import at.falb.games.alcatraz.api.Prisoner;
import ports.in.MoveReceiver;

public class MoveListenerHandler implements MoveListener {

    private MoveReceiver moveReceiver;

    private MoveListenerHandler() {
        // defeat instantiation
    }

    private MoveListenerHandler(MoveReceiver moveReceiver) {
        this.moveReceiver = moveReceiver;
    }

    public static MoveListenerHandler create(MoveReceiver moveReceiver) {
        return new MoveListenerHandler(moveReceiver);
    }


    @Override
    public void moveDone(Player player, Prisoner prisoner, int i, int i1, int i2) {
        moveReceiver.moveDoneMessage(player.getId());
    }

    @Override
    public void gameWon(Player player) {
        // TODO document why this method is empty
    }
}
