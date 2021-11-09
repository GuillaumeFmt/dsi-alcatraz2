package core.usecase;

import at.falb.games.alcatraz.api.IllegalMoveException;
import models.ClientMove;
import models.GameState;
import ports.in.RemoteMoveReceiver;

public class RemoteMoveReceiverUseCase implements RemoteMoveReceiver {

    @Override
    public boolean sendAcknowledge() {
        return true;
    }

    @Override
    public void updateLocalGameState(ClientMove clientMove) {
        try {
            GameState.getGameStateInstance().getAlcatraz().doMove(clientMove.getPlayer(), clientMove.getPrisoner(), clientMove.getRowOrCol(), clientMove.getRow(), clientMove.getCol());
        } catch (IllegalMoveException e) {
            e.printStackTrace();
        }
    }
}
