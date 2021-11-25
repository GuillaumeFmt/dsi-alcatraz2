package view.controller;

import view.LobbyWindow;
import view.WelcomeWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JoinLobbyButtonController implements ActionListener
{
    //TODO: wenn man dazu joined, muss die tabelle bzw. die dazugehörige row aktualisiert werden
    //TODO:beim join soll ein rmi an den server geschickt werden, um die Tabelle zu aktualisieren
    private LobbyWindow lobbyWindow;

    public JoinLobbyButtonController(LobbyWindow lobbyWindow)
    {
        this.lobbyWindow = lobbyWindow;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        lobbyWindow.getTextPane().setText("You clicked onto the join Button");
    }
}
