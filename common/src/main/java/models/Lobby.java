package models;

import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

@ToString
public class Lobby implements Serializable {

    private final UUID lobbyId;
    private final String lobbyName;
    private ClientPlayer lobbyOwner;
    private final ArrayList<ClientPlayer> lobbyParticipants = new ArrayList<>();
    private int participantCount = 0;
    private boolean isStarted;

    public Lobby(String lobbyName, ClientPlayer clientPlayer) {
        this.lobbyId = UUID.randomUUID();
        this.lobbyName = lobbyName;
        this.lobbyOwner = clientPlayer;
        this.lobbyParticipants.add(clientPlayer);
        this.participantCount++;
    }

    public UUID getLobbyId() {
        return lobbyId;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public boolean addLobbyParticipant(ClientPlayer clientPlayer) {
        boolean added = false;
        for (ClientPlayer lobbyParticipant : lobbyParticipants) {
            if (Objects.equals(lobbyParticipant.getPlayerName(), clientPlayer.getPlayerName())) {
                added = true;
                break;
            }
        }
        if (!added) {
            if (!isStarted) {
                if (participantCount < 3) {
                    lobbyParticipants.add(clientPlayer);
                    participantCount++;
                }
            }
        }

        return added;
    }

    public void setLobbyOwner(ClientPlayer lobbyOwner) {
        this.lobbyOwner = lobbyOwner;
    }

    public ClientPlayer getLobbyOwner() {
        return lobbyOwner;
    }

    public ArrayList<ClientPlayer> getLobbyParticipants() {
        return lobbyParticipants;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setStarted(boolean started) {
        isStarted = started;
    }
}
