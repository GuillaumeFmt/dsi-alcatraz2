package core.view.controller;

import adapters.in.AlcatrazGUIReceiverAdapter;
import models.ClientPlayer;
import core.view.LobbyWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaveLobbyButtonController implements ActionListener
{
    //TODO:wenn der user auf den leave lobby button klickt, muss die row aktualisiert werden
    //TODO:wenn der user auf den leave lobby button klickt, muss ein rmi zum server geschickt werden

    private LobbyWindow lobbyWindow;
    private ClientPlayer clientPlayer;

    public LeaveLobbyButtonController(LobbyWindow lobbyWindow, AlcatrazGUIReceiverAdapter guiReceiverAdapter)
    {
        this.lobbyWindow = lobbyWindow;
        clientPlayer = lobbyWindow.getClientPlayer();
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        lobbyWindow.getTextPane().setText("You clicked onto the leave Button");
    }
}
