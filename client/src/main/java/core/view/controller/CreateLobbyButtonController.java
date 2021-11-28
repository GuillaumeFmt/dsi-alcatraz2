package core.view.controller;

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

    public CreateLobbyButtonController(LobbyWindow lobbyWindow)
    {
        this.lobbyWindow = lobbyWindow;
        clientPlayer = lobbyWindow.getClientPlayer();

    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        lobbyWindow.getTextPane().setText("You clicked onto the create Button");
        String lobbyName = JOptionPane.showInputDialog(lobbyWindow,"Enter your lobby name: ","LobbyName",JOptionPane.INFORMATION_MESSAGE);

        lobbyWindow.getAddRowController().addRow(lobbyName,clientPlayer.getPlayerName());


    }
}
