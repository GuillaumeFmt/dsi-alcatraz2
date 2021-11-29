package ports;

import exceptions.ClientNotReachableException;
import exceptions.ServerNotPrimaryException;
import models.ClientPlayer;
import models.Lobby;

import java.util.List;
import java.util.UUID;

public interface ServerLobbyHandler {

    UUID register (ClientPlayer clientPlayer) throws ClientNotReachableException;

    List<Lobby> currentLobbies() throws ClientNotReachableException;

    UUID createLobby(String lobbyName, ClientPlayer clientPlayer) throws ClientNotReachableException;

    List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) throws ClientNotReachableException;

    Boolean leaveLobby(ClientPlayer clientPlayer) throws ClientNotReachableException;

    Boolean startGame(Lobby lobby) throws ClientNotReachableException;

}
