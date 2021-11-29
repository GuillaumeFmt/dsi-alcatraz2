package core;

import core.adapters.out.ServerLobbyHandlerRMIAdapter;
import core.adapters.in.AlcatrazGUIReceiverAdapter;
import core.adapters.in.AlcatrazGameAdapter;
import core.adapters.out.ClientMoverRMIAdapter;
import core.usecase.AlcatrazGuiReceiverUseCase;
import core.usecase.GameInitializer;
import core.usecase.LocalMoveReceiverUseCase;
import models.GameState;
import models.RegistrationServer;
import ports.ServerLobbyHandler;
import ports.in.AlcatrazGUIReceiver;
import ports.out.ClientMover;
import ports.in.LocalMoveReceiver;
import security.AlcatrazSecurityPolicy;
import core.view.WelcomeWindow;

import java.io.IOException;
import java.security.Policy;
import java.util.ArrayList;

public class Client {

    public static void main(String[] args) throws IOException {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        ArrayList<RegistrationServer> servers = new ArrayList<>();
        servers.add(new RegistrationServer("192.168.178.52", 9876));
        servers.add(new RegistrationServer("dsiars01.westeurope.cloudapp.azure.com", 9876));
        servers.add(new RegistrationServer("dsiars02.westeurope.cloudapp.azure.com", 9876));
        servers.add(new RegistrationServer("dsiars03.westeurope.cloudapp.azure.com", 9876));

        ServerLobbyHandler serverLobbyHandler = new ServerLobbyHandlerRMIAdapter("RegistrationServer", servers);

        GameInitializer gameInitializer = new GameInitializer(serverLobbyHandler);

        AlcatrazGUIReceiver alcatrazGUIReceiver = new AlcatrazGuiReceiverUseCase(gameInitializer);
        AlcatrazGUIReceiverAdapter guiReceiverAdapter = new AlcatrazGUIReceiverAdapter(alcatrazGUIReceiver);

        new WelcomeWindow(guiReceiverAdapter);

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
