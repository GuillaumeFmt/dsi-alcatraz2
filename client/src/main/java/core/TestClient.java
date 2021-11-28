package core;

import adapters.ServerLobbyHandlerRMIAdapter;
import adapters.in.AlcatrazGameAdapter;
import adapters.out.ClientMoverRMIAdapter;
import core.usecase.GameInitializer;
import core.usecase.LocalMoveReceiverUseCase;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import models.GameState;
import models.Lobby;
import ports.ServerLobbyHandler;
import ports.in.LocalMoveReceiver;
import ports.out.ClientMover;
import security.AlcatrazSecurityPolicy;

import java.io.IOException;
import java.security.Policy;
import java.util.List;

@Slf4j
public class TestClient {

    public static void main(String[] args) throws IOException {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        ServerLobbyHandler serverLobbyHandler = new ServerLobbyHandlerRMIAdapter(9876, "Server");

        GameInitializer gameInitializer = new GameInitializer(9876, serverLobbyHandler);
        gameInitializer.init("Client 2", 9872);

        List<Lobby> currentLobbies = gameInitializer.getCurrentLobbies();

        currentLobbies.forEach(lobby -> log.info("Lobby: {}", lobby));

        /*
        if (currentLobbies.size() == 1) {
            gameInitializer.joinLobby(currentLobbies.get(0), clientPlayer);
            gameInitializer.leaveLobby(clientPlayer);
        }
        */

        // TODO wait for keyboard input
        System.in.read();

        ClientMover clientMover = new ClientMoverRMIAdapter(9876, "Client 1");

        LocalMoveReceiver localMoveReceiver = new LocalMoveReceiverUseCase(clientMover);

        var alcatrazGame = new AlcatrazGameAdapter(localMoveReceiver, 1, GameState.getGameStateInstance());

        while (true) {

        }
    }
}
