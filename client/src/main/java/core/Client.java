package core;

import adapters.ServerLobbyHandlerRMIAdapter;
import adapters.in.AlcatrazGameAdapter;
import adapters.out.ClientMoverRMIAdapter;
import core.usecase.AcknowledgeUseCase;
import core.usecase.GameInitializer;
import core.usecase.MoveReceiverUseCase;
import ports.ServerLobbyHandler;
import ports.out.ClientMover;
import ports.in.MoveReceiver;
import security.AlcatrazSecurityPolicy;

import java.io.IOException;
import java.security.Policy;

public class Client {
    public static void main(String[] args) throws IOException {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        ServerLobbyHandler serverLobbyHandler = new ServerLobbyHandlerRMIAdapter(9876, "Client 1", "Client 2", 9871);

        GameInitializer gameInitializer = new GameInitializer(serverLobbyHandler);
        gameInitializer.init();

        // TODO wait for keyboard input
        System.in.read();

        ClientMover clientMover = new ClientMoverRMIAdapter(9876, "Client 1", 9871, "Client 2", new AcknowledgeUseCase());
        // clientMover is "Client 2" in this case


        MoveReceiver moveReceiver = new MoveReceiverUseCase(clientMover);

        var alcatrazGame = new AlcatrazGameAdapter(moveReceiver, 0);

        while (true) {

        }
    }
}
