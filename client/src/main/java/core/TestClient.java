package core;

import adapters.ServerLobbyHandlerRMIAdapter;
import adapters.in.AlcatrazGameAdapter;
import adapters.out.ClientMoverRMIAdapter;
import core.usecase.RemoteMoveReceiverUseCase;
import core.usecase.GameInitializer;
import core.usecase.LocalMoveReceiverUseCase;
import models.ClientPlayer;
import models.GameState;
import models.Lobby;
import ports.ServerLobbyHandler;
import ports.in.LocalMoveReceiver;
import ports.out.ClientMover;
import security.AlcatrazSecurityPolicy;

import java.io.IOException;
import java.security.Policy;
import java.util.ArrayList;
import java.util.List;

public class TestClient {
    public static void main(String[] args) throws IOException {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        ServerLobbyHandler serverLobbyHandler = new ServerLobbyHandlerRMIAdapter(9876,"Server");

        GameInitializer gameInitializer = new GameInitializer(9876, serverLobbyHandler,"Client 2", 9872);
        gameInitializer.init();

        List<Lobby> currentLobbies = gameInitializer.getCurrentLobbies();
        currentLobbies.forEach(lobby -> System.out.println("LobbyID: " + lobby.getLobbyId().toString() + " LobbyName: " + lobby.getLobbyName() + " LobbyParticipants: " + lobby.getLobbyOwner().getPlayerName()));
        if (currentLobbies.size() == 1) {
            ClientPlayer testPlayer = new ClientPlayer("0.0.0.0", 9872, "Client 2");
            gameInitializer.joinLobby(currentLobbies.get(0), testPlayer);
            gameInitializer.leaveLobby(testPlayer);
        }


        // TODO wait for keyboard input
        System.in.read();

        ClientMover clientMover = new ClientMoverRMIAdapter(9876, "Client 1");

        LocalMoveReceiver localMoveReceiver = new LocalMoveReceiverUseCase(clientMover);

//        System.out.println(clientMover.sendMove(new ClientMove()));

        var alcatrazGame = new AlcatrazGameAdapter(localMoveReceiver, 1, GameState.getGameStateInstance());

        while (true) {

        }
    }
}
