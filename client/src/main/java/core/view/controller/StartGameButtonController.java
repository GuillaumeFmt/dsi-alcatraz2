package core.view.controller;

import adapters.in.AlcatrazGUIReceiverAdapter;
import models.ClientPlayer;
import core.view.LobbyWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartGameButtonController implements ActionListener
{
    //TODO:wenn der user auf den start button klickt, muss das spiel starten, wenn mindestens zwei spieler in der lobby vorhanden sind

    private LobbyWindow lobbyWindow;
    private ClientPlayer clientPlayer;

    public StartGameButtonController(LobbyWindow lobbyWindow, AlcatrazGUIReceiverAdapter guiReceiverAdapter)
    {
        this.lobbyWindow = lobbyWindow;
        clientPlayer = lobbyWindow.getClientPlayer();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        lobbyWindow.getTextPane().setText("You clicked onto the start Button");
        System.out.println(clientPlayer.getPlayerName());
    }
}

