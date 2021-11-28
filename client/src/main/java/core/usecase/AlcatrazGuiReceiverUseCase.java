package core.usecase;

import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import models.Lobby;
import ports.in.AlcatrazGUIReceiver;

import java.util.List;
import java.util.UUID;

@Slf4j
public class AlcatrazGuiReceiverUseCase implements AlcatrazGUIReceiver {


@Override
    public void createUser(String userName, int port) {
        log.info("Username: {}, Port: {}", userName, port);
    }

    @Override
    public Lobby joinLobby(Lobby lobby, ClientPlayer clientPlayer) {
        return null;
    }

    @Override
    public Boolean leaveLobby(UUID lobbyId) {
        return false;
    }

    @Override
    public List<Lobby> getLobbies() {
        return null;
    }
}
