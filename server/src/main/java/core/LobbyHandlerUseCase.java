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

    @Override
    public List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) {
        ArrayList<ClientPlayer> ret = null;
        for (Lobby currentLobby : lobbies) {
            if (currentLobby.getLobbyId().equals(lobby.getLobbyId())) {
                currentLobby.addLobbyParticipant(clientPlayer);
                ret = currentLobby.getLobbyParticipants();
                System.out.println("Client: " + clientPlayer.getPlayerName() + " joined the lobby: " + lobby.getLobbyId() + " name: " +lobby.getLobbyName());
            }
        }
        return (List<ClientPlayer>) ret;
    }

    @Override
    public boolean leavLobby(ClientPlayer clientPlayer) {
        boolean ret = false;
        for (Lobby currentLobby: lobbies) {
            List<ClientPlayer> players = currentLobby.getLobbyParticipants();
            for (ClientPlayer currentPlayer : players) {
                if (currentPlayer.getPlayerName().equals(clientPlayer.getPlayerName())) {
                    currentLobby.getLobbyParticipants().remove(currentPlayer);
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }
}
