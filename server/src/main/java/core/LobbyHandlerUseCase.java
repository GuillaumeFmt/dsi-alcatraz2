package core;

import models.ClientPlayer;
import models.Lobby;
import ports.in.LobbyHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LobbyHandlerUseCase implements LobbyHandler {

    private final ArrayList<Lobby> lobbies = new ArrayList<>();

    @Override
    public UUID createLobby(String lobbyName, ClientPlayer clientPlayer) {
        Lobby lobby = new Lobby(lobbyName, clientPlayer);
        lobbies.add(lobby);
        return lobby.getLobbyId();
    }

    @Override
    public List<Lobby> getCurrentLobbies() {
        return lobbies;
    }
}
