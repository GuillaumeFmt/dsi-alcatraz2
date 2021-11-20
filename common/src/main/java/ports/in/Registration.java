package ports.in;

import models.ClientPlayer;

import java.util.List;
import java.util.UUID;

public interface Registration {

    public UUID addClientPlayer(ClientPlayer clientPlayer);
    public List<ClientPlayer> getClientPlayers();
}
