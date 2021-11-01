package adapters;

import models.ClientPlayer;
import models.Lobby;
import ports.ServerLobbyHandler;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ServerLobbyHandlerRMIAdapter implements ServerLobbyHandler {

    @Override
    public UUID register(ClientPlayer clientPlayer) {
        return null;
    }

    @Override
    public List<Lobby> currentLobbies() {
        return Collections.emptyList();
    }

    @Override
    public Lobby createLobby(String lobbyName, ClientPlayer clientPlayer) {
        return null;
    }

    @Override
    public List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) {
        return Collections.emptyList();
    }

    @Override
    public Boolean leaveLobby(ClientPlayer clientPlayer) {
        return false;
    }

    @Override
    public Boolean startGame(Lobby lobby) {
        return false;
    }
}
