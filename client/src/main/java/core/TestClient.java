package core;

import adapters.in.AlcatrazGameAdapter;
import adapters.out.ClientMoverRMIAdapter;
import core.usecase.LocalMoveReceiverUseCase;
import lombok.extern.slf4j.Slf4j;
import models.GameState;
import ports.in.LocalMoveReceiver;
import ports.out.ClientMover;
import security.AlcatrazSecurityPolicy;

import java.io.IOException;
import java.security.Policy;

@Slf4j
public class TestClient {
    private static final String[] servers = {"localhost:9876", "dsiars01.westeurope.cloudapp.azure.com:9876", "dsiars02.westeurope.cloudapp.azure.com:9876", "dsiars03.westeurope.cloudapp.azure.com:9876"};


    public static void main(String[] args) throws IOException {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        //ServerLobbyHandler serverLobbyHandler = new ServerLobbyHandlerRMIAdapter(9876, "Server");

        //GameInitializer gameInitializer = new GameInitializer(servers, serverLobbyHandler);
        //gameInitializer.init("Client 2", 9872);

        //List<Lobby> currentLobbies = gameInitializer.getCurrentLobbies();

        //currentLobbies.forEach(lobby -> log.info("Lobby: {}", lobby));

        /*
        if (currentLobbies.size() == 1) {
            gameInitializer.joinLobby(currentLobbies.get(0), clientPlayer);
            gameInitializer.leaveLobby(clientPlayer);
        }
        */

        // TODO wait for keyboard input
        /*System.in.read();

        ClientMover clientMover = new ClientMoverRMIAdapter(9876, "Client 1");

        LocalMoveReceiver localMoveReceiver = new LocalMoveReceiverUseCase(clientMover);

        var alcatrazGame = new AlcatrazGameAdapter(localMoveReceiver, 1, GameState.getGameStateInstance());*/

        while (true) {

        }
    }
}
