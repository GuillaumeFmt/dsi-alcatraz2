package ports;

import exceptions.ClientNotReachableException;
import exceptions.StartGameException;
import models.ClientPlayer;
import models.Lobby;

import java.util.List;
import java.util.UUID;

public interface ServerLobbyHandler {

    UUID register (ClientPlayer clientPlayer);

    List<Lobby> currentLobbies();

    UUID createLobby(String lobbyName, ClientPlayer clientPlayer);

    List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer);

    Boolean leaveLobby(String playerName, UUID lobbyId);

    Boolean startGame(UUID lobbyId) throws StartGameException;

}
