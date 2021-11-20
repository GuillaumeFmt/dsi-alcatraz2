package ports.in;

import models.ClientPlayer;
import models.Lobby;

import java.util.List;
import java.util.UUID;

public interface LobbyHandler {

    public UUID createLobby(String lobbyName, ClientPlayer clientPlayer);
    public List<Lobby> getCurrentLobbies();

}
