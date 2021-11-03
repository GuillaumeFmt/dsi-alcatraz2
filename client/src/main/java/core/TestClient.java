package core;

import adapters.ServerLobbyHandlerRMIAdapter;
import adapters.in.AlcatrazGameAdapter;
import adapters.out.ClientMoverRMIAdapter;
import core.usecase.AcknowledgeUseCase;
import core.usecase.GameInitializer;
import core.usecase.MoveReceiverUseCase;
import models.ClientMove;
import ports.ServerLobbyHandler;
import ports.in.MoveReceiver;
import ports.out.ClientMover;
import security.AlcatrazSecurityPolicy;

import java.io.IOException;
import java.security.Policy;

public class TestClient {
    public static void main(String[] args) throws IOException {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        ServerLobbyHandler serverLobbyHandler = new ServerLobbyHandlerRMIAdapter(9876, "Client 2", "Client 1", 9872);

        GameInitializer gameInitializer = new GameInitializer(serverLobbyHandler);
        gameInitializer.init();

        // TODO wait for keyboard input
        System.in.read();

        ClientMover clientMover = new ClientMoverRMIAdapter(9876, "Client 2", 9872, "Client 1", new AcknowledgeUseCase());

        MoveReceiver moveReceiver = new MoveReceiverUseCase(clientMover);

        System.out.println(clientMover.sendMove(new ClientMove()));

        var alcatrazGame = new AlcatrazGameAdapter(moveReceiver, 1);

        while (true) {

        }
    }
}
