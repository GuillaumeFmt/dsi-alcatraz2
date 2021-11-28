package ports.out;

import exceptions.ClientNotReachableException;
import models.ClientMove;
import models.ClientPlayer;

public interface ClientMover {
    public boolean sendMove(ClientMove clientMove) throws ClientNotReachableException;

    public boolean nextPlayerMove(ClientPlayer clientPlayer);
}
