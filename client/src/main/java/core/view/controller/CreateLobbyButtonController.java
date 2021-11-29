package core.view.controller;

import core.adapters.in.AlcatrazGUIReceiverAdapter;
import models.ClientPlayer;
import core.view.LobbyWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateLobbyButtonController implements ActionListener
{

    //done wenn man eine neue lobby erstellt, soll eine neue row eingestellt werden


    private LobbyWindow lobbyWindow;
    private ClientPlayer clientPlayer;
    private AlcatrazGUIReceiverAdapter guiReceiverAdapter;

    public CreateLobbyButtonController(LobbyWindow lobbyWindow, AlcatrazGUIReceiverAdapter guiReceiverAdapter)
    {
        this.lobbyWindow = lobbyWindow;
        clientPlayer = lobbyWindow.getClientPlayer();
        this.guiReceiverAdapter = guiReceiverAdapter;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        lobbyWindow.getTextPane().setText("You clicked onto the create Button");
        String lobbyName = JOptionPane.showInputDialog(lobbyWindow,"Enter your lobby name: ","LobbyName",JOptionPane.INFORMATION_MESSAGE);

        // TODO: implement logic to create new lobby and get new lobby list for the UI
        //lobbyWindow.getAddRowController().addRow(lobby.getLobbyId(), lobby.getLobbyName(), lobbyName,clientPlayer.getPlayerName(), lobby.getLobbyParticipants().size(), lobby.isStarted());
        guiReceiverAdapter.createLobby(lobbyName);
        lobbyWindow.createTable(guiReceiverAdapter);
    }
}
