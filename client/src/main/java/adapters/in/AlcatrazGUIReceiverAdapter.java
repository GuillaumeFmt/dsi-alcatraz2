package adapters.in;

import core.domain.ClientState;
import models.Lobby;
import ports.in.AlcatrazGUIReceiver;

import java.util.List;
import java.util.UUID;

public class AlcatrazGUIReceiverAdapter {

    private final AlcatrazGUIReceiver alcatrazGUIReceiver;

    public AlcatrazGUIReceiverAdapter(AlcatrazGUIReceiver alcatrazGUIReceiver) {
        this.alcatrazGUIReceiver = alcatrazGUIReceiver;
    }

    public void createUser(String userName, int port) {
        alcatrazGUIReceiver.createUser(userName, port);
    }

    public List<Lobby> getLobbies() {
        return alcatrazGUIReceiver.getLobbies();
    }

    public void createLobby(String lobbyName) {
        alcatrazGUIReceiver.createLobby(lobbyName);
    }

    public void joinLobby(Lobby lobby) {
        alcatrazGUIReceiver.joinLobby(lobby, ClientState.getInstance().getLocalClientPlayer());
    }

    public void leaveLobby(UUID lobbyToLeave) {
        alcatrazGUIReceiver.leaveLobby(lobbyToLeave);
    }

    // TODO: implement the other methods...

}
