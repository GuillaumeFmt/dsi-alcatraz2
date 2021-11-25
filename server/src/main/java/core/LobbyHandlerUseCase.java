package core;

import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import models.Lobby;
import ports.in.LobbyHandler;

import java.util.*;

@Slf4j
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
        if (lobbies.stream().filter(streamLobby -> streamLobby.getLobbyId().equals(lobby.getLobbyId())).count() == 1) {
            try {
                Lobby lobbyToJoin = lobbies.stream()
                        .filter(streamLobby -> streamLobby.getLobbyId().equals(lobby.getLobbyId()))
                        .findFirst().orElseThrow(Exception::new);

                lobbyToJoin.addLobbyParticipant(clientPlayer);
                log.info("ClientPlayer: {} joined the Lobby: {}", clientPlayer, lobby);

                return lobbyToJoin.getLobbyParticipants();
            } catch (Exception e) {
                log.error("Could not add player to lobby, Lobby {} not found! - Exception: {}", lobby, e.getMessage());
            }
        }
        return Collections.emptyList();
    }

    @Override
    public boolean leaveLobby(ClientPlayer clientPlayer) {
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
