package ports.in;

import at.falb.games.alcatraz.api.Player;
import at.falb.games.alcatraz.api.Prisoner;
import models.ClientMove;

public interface LocalMoveReceiver {

    public void updateLocalMove(ClientMove clientMove);

    public void moveDoneMessage(Player player, Prisoner prisoner,  int rowOrCol, int row, int col);
}
