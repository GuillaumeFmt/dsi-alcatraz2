package core.view.controller;

import adapters.in.AlcatrazGUIReceiverAdapter;
import core.domain.ClientState;
import models.ClientPlayer;
import core.view.LobbyWindow;
import core.view.WelcomeWindow;
import models.GameState;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveButtonController implements ActionListener
{
    //TODO: Bei Eingabe des Usernames soll ein neuer Clientplayer erstellt werden
    //TODO:Bestehende lobbys müssen im Lobbywindow angezeigt werden (in den Konstruktor bzw. beim erstellen der Tabelle rein?)


    private WelcomeWindow welcomeWindow;
    private String userName;
    private int userPort;
    private ClientPlayer clientPlayer;
    private AlcatrazGUIReceiverAdapter guiReceiverAdapter;

    public SaveButtonController(WelcomeWindow welcomeWindow, AlcatrazGUIReceiverAdapter guiReceiver)
    {
        this.welcomeWindow = welcomeWindow;
        this.guiReceiverAdapter = guiReceiver;
    }


    @Override
    public void actionPerformed(ActionEvent e)
    {
        userName = welcomeWindow.getPlayerName().getText();
        userPort = Integer.parseInt(welcomeWindow.getPlayerPort().getText());
        //System.out.println("getplayername: "+userName);
        clientPlayer = new ClientPlayer("",533, userName);
        guiReceiverAdapter.createUser(userName, userPort);

        System.out.println("getplayer name client "+clientPlayer.getPlayerName());

        LobbyWindow lobby = new LobbyWindow(ClientState.getInstance().getLocalClientPlayer(), guiReceiverAdapter);
        lobby.setName(userName);
        lobby.getTextPane().setText("Hello "+userName);
        //TODO: set ip and port with proper values

        welcomeWindow.setVisible(false);

    }
}
