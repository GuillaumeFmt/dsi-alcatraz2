package view.controller;

import view.LobbyWindow;

import static view.LobbyWindow.lobbyIDCounter;

//TODO: bei erstellung einer neuen lobby soll ein rmi an den server geschickt werden, um die tabelle zu aktualisieren
public class AddRowController
{
    private LobbyWindow lobbyWindow;


    public AddRowController(LobbyWindow lobbyWindow)
    {
        this.lobbyWindow = lobbyWindow;
    }

    public void addRow(String lobbyName,String lobbyOwner)
    {
        lobbyWindow.getTableModel().addRow(new Object[]{++lobbyIDCounter,lobbyName,lobbyOwner,lobbyOwner,1,false});
        //TODO:send RMI to server

    }

}
