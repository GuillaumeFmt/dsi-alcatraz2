package ports.out;

import models.ClientMove;
import models.ClientPlayer;

public interface ClientMover {
    public boolean sendMove(ClientMove clientMove);

    public boolean nextPlayerMove(ClientPlayer clientPlayer);
}
