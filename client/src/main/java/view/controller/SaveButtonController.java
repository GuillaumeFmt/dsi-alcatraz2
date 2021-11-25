package view.controller;

import models.ClientPlayer;
import view.LobbyWindow;
import view.WelcomeWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveButtonController implements ActionListener
{
    //TODO: Bei Eingabe des Usernames soll ein neuer Clientplayer erstellt werden
    //TODO:Bestehende lobbys müssen im Lobbywindow angezeigt werden (in den Konstruktor bzw. beim erstellen der Tabelle rein?)


    private WelcomeWindow welcomeWindow;
    private String userName;
    private ClientPlayer clientPlayer;

    public SaveButtonController(WelcomeWindow welcomeWindow)
    {
        this.welcomeWindow = welcomeWindow;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        userName = welcomeWindow.getPlayerName().getText();
        //System.out.println("getplayername: "+userName);
        clientPlayer = new ClientPlayer("",533,userName);
        //System.out.println("getplayer name client "+clientPlayer.getPlayerName());
        LobbyWindow lobby = new LobbyWindow(clientPlayer);
        lobby.setName(userName);
        lobby.getTextPane().setText("Hello "+userName);
        //TODO: set ip and port with proper values

        welcomeWindow.setVisible(false);






    }
}
