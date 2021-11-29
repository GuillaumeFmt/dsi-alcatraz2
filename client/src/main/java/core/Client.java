package core;

import adapters.out.ServerLobbyHandlerRMIAdapter;
import adapters.in.AlcatrazGUIReceiverAdapter;
import adapters.in.AlcatrazGameAdapter;
import adapters.out.ClientMoverRMIAdapter;
import core.usecase.AlcatrazGuiReceiverUseCase;
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
import java.io.InputStream;
import java.security.Policy;
import java.util.ArrayList;
import java.util.Properties;

public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        ArrayList<RegistrationServer> servers = new ArrayList<>();

        try (InputStream input = Client.class.getClassLoader().getResourceAsStream("server.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                System.out.println("Sorry, unable to find server.properties");
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            servers.add(new RegistrationServer(prop.getProperty("server1"), 9876));
            servers.add(new RegistrationServer(prop.getProperty("server2"), 9876));
            servers.add(new RegistrationServer(prop.getProperty("server3"), 9876));

        } catch (IOException ex) {
            ex.printStackTrace();
        }


        ServerLobbyHandler serverLobbyHandler = new ServerLobbyHandlerRMIAdapter("RegistrationServer", servers);
        AlcatrazGUIReceiver alcatrazGUIReceiver = new AlcatrazGuiReceiverUseCase(serverLobbyHandler);
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
