package core;

import adapters.ServerLobbyHandlerRMIAdapter;
import adapters.in.AlcatrazGameAdapter;
import adapters.out.ClientMoverRMIAdapter;
import core.usecase.GameInitializer;
import core.usecase.LocalMoveReceiverUseCase;
import models.ClientPlayer;
import models.GameState;
import models.Lobby;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ports.ServerLobbyHandler;
import ports.in.LocalMoveReceiver;
import ports.out.ClientMover;
import security.AlcatrazSecurityPolicy;

import java.io.IOException;
import java.security.Policy;
import java.util.List;

public class TestClient {

    private static final Logger logger = LoggerFactory.getLogger(TestClient.class);

    public static void main(String[] args) throws IOException {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        ServerLobbyHandler serverLobbyHandler = new ServerLobbyHandlerRMIAdapter(9876, "Server");
        ClientPlayer clientPlayer = new ClientPlayer("0.0.0.0", 9872, "Client 2");

        GameInitializer gameInitializer = new GameInitializer(9876, serverLobbyHandler, clientPlayer);
        gameInitializer.init();

        List<Lobby> currentLobbies = gameInitializer.getCurrentLobbies();

        currentLobbies.forEach(lobby -> logger.info("Lobby: {}", lobby));

        if (currentLobbies.size() == 1) {
            gameInitializer.joinLobby(currentLobbies.get(0), clientPlayer);
            gameInitializer.leaveLobby(clientPlayer);
        }

        // TODO wait for keyboard input
        System.in.read();

        ClientMover clientMover = new ClientMoverRMIAdapter(9876, "Client 1");

        LocalMoveReceiver localMoveReceiver = new LocalMoveReceiverUseCase(clientMover);

        var alcatrazGame = new AlcatrazGameAdapter(localMoveReceiver, 1, GameState.getGameStateInstance());

        while (true) {

        }
    }
}
