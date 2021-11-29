package core.view.controller;

import adapters.in.AlcatrazGUIReceiverAdapter;
import core.domain.ClientState;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import core.view.LobbyWindow;
import models.Lobby;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class LeaveLobbyButtonController implements ActionListener
{
    //TODO:wenn der user auf den leave lobby button klickt, muss die row aktualisiert werden
    //TODO:wenn der user auf den leave lobby button klickt, muss ein rmi zum server geschickt werden

    private LobbyWindow lobbyWindow;
    private ClientPlayer clientPlayer;
    private AlcatrazGUIReceiverAdapter guiReceiverAdapter;

    public LeaveLobbyButtonController(LobbyWindow lobbyWindow, AlcatrazGUIReceiverAdapter guiReceiverAdapter)
    {
        this.lobbyWindow = lobbyWindow;
        clientPlayer = lobbyWindow.getClientPlayer();
        this.guiReceiverAdapter = guiReceiverAdapter;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {


        int row = lobbyWindow.getLobbyTable().getSelectedRow();
        System.out.println(row);
        UUID lobbyId = (UUID) lobbyWindow.getLobbyTable().getValueAt(row, 0);
        String lobbyName = String.valueOf(lobbyWindow.getLobbyTable().getValueAt(row, 1));


        guiReceiverAdapter.leaveLobby(lobbyId);

        log.info("Selected LobbyID is {}",lobbyId.toString());
        lobbyWindow.getTextPane().setText("You left the "+lobbyName+ " lobby");
        lobbyWindow.createTable(guiReceiverAdapter);


    }
}
