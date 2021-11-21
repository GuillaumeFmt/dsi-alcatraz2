package core.usecase;

import at.falb.games.alcatraz.api.Player;
import at.falb.games.alcatraz.api.Prisoner;
import models.ClientMove;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.out.ClientMover;
import ports.in.LocalMoveReceiver;

public class LocalMoveReceiverUseCase implements LocalMoveReceiver {

    private static final Logger logger = LoggerFactory.getLogger(LocalMoveReceiverUseCase.class);

    private ClientMover clientMover;

    public LocalMoveReceiverUseCase(ClientMover clientMover) {
        this.clientMover = clientMover;
    }

    @Override
    public void updateLocalMove(ClientMove clientMove) {
        // TODO
    }

    @Override
    public void moveDoneMessage(Player player, Prisoner prisoner, int rowOrCol, int row, int col) {
        logger.info("Move done! - Player: {} - Prisoner: {}, row/col: {}, row: {}, col: {}",
                player,
                prisoner,
                rowOrCol,
                row,
                col);
        clientMover.sendMove(new ClientMove(0, player, prisoner, rowOrCol, row, col));
    }
}
