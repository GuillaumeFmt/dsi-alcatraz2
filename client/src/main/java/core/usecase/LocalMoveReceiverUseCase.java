package core.usecase;

import at.falb.games.alcatraz.api.Player;
import at.falb.games.alcatraz.api.Prisoner;
import exceptions.ClientNotReachableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.ClientMove;
import ports.out.ClientMover;
import ports.in.LocalMoveReceiver;

@Slf4j
@RequiredArgsConstructor
public class LocalMoveReceiverUseCase implements LocalMoveReceiver {

    private final ClientMover clientMover;

    @Override
    public void updateLocalMove(ClientMove clientMove) {
        // TODO
    }

    @Override
    public void localMoveReceived(Player player, Prisoner prisoner, int rowOrCol, int row, int col) {
        log.info("Move done! - Player: {} - Prisoner: {}, row/col: {}, row: {}, col: {}",
                player,
                prisoner,
                rowOrCol,
                row,
                col);
        try {
            clientMover.sendMove(new ClientMove(0, player, prisoner, rowOrCol, row, col));
        } catch (ClientNotReachableException e) {
            log.error("The client was not reacting: {0}", e);
            System.exit(0);
        }
    }
}
