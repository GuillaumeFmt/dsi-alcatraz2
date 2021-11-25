package view.controller;

import view.LobbyWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LeaveLobbyButtonController implements ActionListener
{
    //TODO:wenn der user auf den leave lobby button klickt, muss die row aktualisiert werden
    //TODO:wenn der user auf den leave lobby button klickt, muss ein rmi zum server geschickt werden

    private LobbyWindow lobbyWindow;

    public LeaveLobbyButtonController(LobbyWindow lobbyWindow)
    {
        this.lobbyWindow = lobbyWindow;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        lobbyWindow.getTextPane().setText("You clicked onto the leave Button");
    }
}
