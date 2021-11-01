package ports;

import models.ClientPlayer;
import java.util.List;

public interface ClientLobbyHandler {

    boolean isAlive();

    boolean initGame(List<ClientPlayer> clientPlayerList);
}
