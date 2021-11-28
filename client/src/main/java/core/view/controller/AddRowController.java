package core.view.controller;

import core.view.LobbyWindow;

import java.util.UUID;

import static core.view.LobbyWindow.lobbyIDCounter;

//TODO: bei erstellung einer neuen lobby soll ein rmi an den server geschickt werden, um die tabelle zu aktualisieren
public class AddRowController
{
    private LobbyWindow lobbyWindow;


    public AddRowController(LobbyWindow lobbyWindow)
    {
        this.lobbyWindow = lobbyWindow;
    }

    public void addRow(UUID lobbyId, String lobbyName, String lobbyOwner, String participants, int participantAmount, boolean started)
    {
        lobbyWindow.getTableModel().addRow(new Object[]{lobbyId, lobbyName, lobbyOwner, participants, participantAmount, started});
        //TODO:send RMI to server

    }

}
