package core.view.controller;

import adapters.in.AlcatrazGUIReceiverAdapter;
import core.view.LobbyWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateTableController implements ActionListener {
    LobbyWindow lobbyWindow;
    AlcatrazGUIReceiverAdapter guiReceiverAdapter;
    public UpdateTableController(LobbyWindow lobbyWindow, AlcatrazGUIReceiverAdapter guiReceiverAdapter) {
        this.lobbyWindow = lobbyWindow;
        this.guiReceiverAdapter = guiReceiverAdapter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.lobbyWindow.createTable(this.guiReceiverAdapter);
    }
}
