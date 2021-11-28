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
        try {
            gameInitializer.registerUser(userName, port);// register user on server
        } catch (ClientNotReachableException e) {
            System.exit(0);
        }
    }

    @Override
    public Lobby createLobby(String lobbyName) {
        try {
            gameInitializer.createLobby(lobbyName);
        } catch (ClientNotReachableException e) {
            e.printStackTrace();
        }
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
        try {
            return gameInitializer.getCurrentLobbies();
        } catch (ClientNotReachableException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
