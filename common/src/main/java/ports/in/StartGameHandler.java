package ports.in;

import exceptions.LobbyException;
import exceptions.StartGameException;

import java.util.UUID;

public interface StartGameHandler {
    Boolean startGame(UUID lobby) throws LobbyException, StartGameException;
}
