package ports.in;

import models.ClientPlayer;
import models.Lobby;

import java.util.List;
import java.util.UUID;

public interface AlcatrazGUIReceiver {

    public void createUser(String userName, int port);

    public Lobby joinLobby(Lobby lobby, ClientPlayer clientPlayer);

    public Boolean leaveLobby(UUID lobbyId);

    public List<Lobby> getLobbies();
}
