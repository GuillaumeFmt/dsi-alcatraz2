package core.usecase;

import exceptions.ClientNotReachableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import models.Lobby;
import ports.in.AlcatrazGUIReceiver;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor// This creates a constructor (with init of the gameInitializer variable) automatically
public class AlcatrazGuiReceiverUseCase implements AlcatrazGUIReceiver {

    private final GameInitializer gameInitializer;

    @Override
    public void createUser(String userName, int port) {
        log.info("Username: {}, Port: {}", userName, port);
        gameInitializer.registerUser(userName, port);// register user on server
    }

    @Override
    public Lobby createLobby(String lobbyName) {
        gameInitializer.createLobby(lobbyName);
        return null;
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
        return gameInitializer.getCurrentLobbies();
    }
}
