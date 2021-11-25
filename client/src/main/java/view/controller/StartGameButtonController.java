package view.controller;

import view.LobbyWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartGameButtonController implements ActionListener
{
    //TODO:wenn der user auf den start button klickt, muss das spiel starten, wenn mindestens zwei spieler in der lobby vorhanden sind

    private LobbyWindow lobbyWindow;

    public StartGameButtonController(LobbyWindow lobbyWindow)
    {
        this.lobbyWindow = lobbyWindow;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        lobbyWindow.getTextPane().setText("You clicked onto the start Button");
    }
}

