package models;

import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

    public void addLobbyParticipant(ClientPlayer clientPlayer) {
        if (canPlayerJoin(clientPlayer)) {
            lobbyParticipants.add(clientPlayer);
        }
    }

    public void setLobbyOwner(ClientPlayer lobbyOwner) {
        this.lobbyOwner = lobbyOwner;
    }

    public ClientPlayer getLobbyOwner() {
        return lobbyOwner;
    }

    public List<ClientPlayer> getLobbyParticipants() {
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

    private boolean canPlayerJoin(ClientPlayer clientPlayer) {
        return lobbyParticipants.stream().noneMatch(participant -> participant.equals(clientPlayer)) && !isStarted && participantCount <= 3;
    }
}
