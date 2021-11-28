package ports;

import exceptions.ServerNotPrimaryException;
import models.ClientPlayer;
import models.Lobby;

import java.util.List;
import java.util.UUID;

public interface ServerLobbyHandler {

    UUID register (ClientPlayer clientPlayer) throws ServerNotPrimaryException;

    List<Lobby> currentLobbies() throws ServerNotPrimaryException;

    UUID createLobby(String lobbyName, ClientPlayer clientPlayer) throws ServerNotPrimaryException;

    List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) throws ServerNotPrimaryException;

    Boolean leaveLobby(ClientPlayer clientPlayer) throws ServerNotPrimaryException;

    Boolean startGame(Lobby lobby) throws ServerNotPrimaryException;

}
