package view.controller;

import view.LobbyWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateLobbyButtonController implements ActionListener
{

    //TODO: wenn man eine neue lobby erstellt, soll eine neue row eingestellt werden und die muss auch am server aktualisiert werden
    //TODO: bei erstellung einer neuen lobby soll ein rmi an den server geschickt werden, um die tabelle zu aktualisieren

    private LobbyWindow lobbyWindow;

    public CreateLobbyButtonController(LobbyWindow lobbyWindow)
    {
        this.lobbyWindow = lobbyWindow;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        lobbyWindow.getTextPane().setText("You clicked onto the create Button");
    }
}
