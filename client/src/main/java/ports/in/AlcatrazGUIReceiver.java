package ports.in;

import exceptions.StartGameException;
import models.ClientPlayer;
import models.Lobby;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public interface AlcatrazGUIReceiver {

    public void createUser(String userName, int port);

    public Lobby createLobby(String lobbyName);

    public Lobby joinLobby(Lobby lobby, ClientPlayer clientPlayer);

    public Boolean leaveLobby(UUID lobbyId);

    public List<Lobby> getLobbies();

    public Boolean startGame(UUID lobbyId);
}
