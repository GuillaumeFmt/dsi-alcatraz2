package core.view.controller;

import adapters.in.AlcatrazGUIReceiverAdapter;
import core.domain.ClientState;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import core.view.LobbyWindow;
import models.GameState;
import models.Lobby;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class JoinLobbyButtonController implements ActionListener
{
    //TODO: wenn man dazu joined, muss die tabelle bzw. die dazugehörige row aktualisiert werden
    //TODO:beim join soll ein rmi an den server geschickt werden, um die Tabelle zu aktualisieren
    private LobbyWindow lobbyWindow;
    private ClientPlayer clientPlayer;
    private AlcatrazGUIReceiverAdapter guiReceiverAdapter;


    public JoinLobbyButtonController(LobbyWindow lobbyWindow, AlcatrazGUIReceiverAdapter guiReceiverAdapter)
    {
        this.guiReceiverAdapter = guiReceiverAdapter;
        this.lobbyWindow = lobbyWindow;
        clientPlayer = lobbyWindow.getClientPlayer();
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        int row = lobbyWindow.getLobbyTable().getSelectedRow();
        UUID lobbyId = (UUID) lobbyWindow.getLobbyTable().getValueAt(row, 0);

        String lobbyName = String.valueOf(lobbyWindow.getLobbyTable().getValueAt(row, 1));

        Optional<Lobby> first = ClientState.getInstance().getCurrentLobbies().stream().filter(l -> l.getLobbyId() == lobbyId).findFirst();
        Lobby lobbyToJoin = first.orElseThrow();

        guiReceiverAdapter.joinLobby(lobbyToJoin);

        log.info("Selected lobbId is {}", lobbyId.toString());
        lobbyWindow.getTextPane().setText("You joined onto the "+lobbyName+ " lobby");
        lobbyWindow.createTable(guiReceiverAdapter);
        lobbyWindow.getLeaveLobbyButton().setEnabled(true);
    }
}
