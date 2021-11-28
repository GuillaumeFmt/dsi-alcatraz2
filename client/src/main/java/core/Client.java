package core;

import adapters.ServerLobbyHandlerRMIAdapter;
import adapters.in.AlcatrazGUIReceiverAdapter;
import adapters.in.AlcatrazGameAdapter;
import adapters.out.ClientMoverRMIAdapter;
import core.usecase.AlcatrazGuiReceiverUseCase;
import core.usecase.GameInitializer;
import core.usecase.LocalMoveReceiverUseCase;
import models.ClientPlayer;
import models.GameState;
import ports.ServerLobbyHandler;
import ports.in.AlcatrazGUIReceiver;
import ports.out.ClientMover;
import ports.in.LocalMoveReceiver;
import security.AlcatrazSecurityPolicy;
import core.view.WelcomeWindow;

import java.io.IOException;
import java.security.Policy;

public class Client {
    public static void main(String[] args) throws IOException {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        AlcatrazGUIReceiver alcatrazGUIReceiver = new AlcatrazGuiReceiverUseCase();
        AlcatrazGUIReceiverAdapter guiReceiverAdapter = new AlcatrazGUIReceiverAdapter(alcatrazGUIReceiver);
        WelcomeWindow welcomeWindow = new WelcomeWindow(guiReceiverAdapter);

        ServerLobbyHandler serverLobbyHandler = new ServerLobbyHandlerRMIAdapter(9876, "Server");
        ClientPlayer clientPlayer = new ClientPlayer("0.0.0.0", 9871, "Client 1");

        GameInitializer gameInitializer = new GameInitializer(9876, serverLobbyHandler, clientPlayer);
        gameInitializer.init();
        gameInitializer.createLobby("Lobby 1");

        // TODO wait for keyboard input

        //attach the gui here
        System.in.read();

        ClientMover clientMover = new ClientMoverRMIAdapter(9876, "Client 2");
        // clientMover is "Client 2" in this case

        LocalMoveReceiver localMoveReceiver = new LocalMoveReceiverUseCase(clientMover);

        var alcatrazGame = new AlcatrazGameAdapter(localMoveReceiver, 0, GameState.getGameStateInstance());

        while (true) {

        }
    }
}
