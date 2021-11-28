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
    private static final String[] servers = {"192.168.178.52:9876", "dsiars01.westeurope.cloudapp.azure.com:9876", "dsiars02.westeurope.cloudapp.azure.com:9876", "dsiars03.westeurope.cloudapp.azure.com:9876"};

    public static void main(String[] args) throws IOException {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        ServerLobbyHandler serverLobbyHandler = new ServerLobbyHandlerRMIAdapter(9876, "Server");

        GameInitializer gameInitializer = new GameInitializer(servers, serverLobbyHandler);

        AlcatrazGUIReceiver alcatrazGUIReceiver = new AlcatrazGuiReceiverUseCase(gameInitializer);
        AlcatrazGUIReceiverAdapter guiReceiverAdapter = new AlcatrazGUIReceiverAdapter(alcatrazGUIReceiver);
        System.in.read(); // dirty workaround for timing issue
        WelcomeWindow welcomeWindow = new WelcomeWindow(guiReceiverAdapter);

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
