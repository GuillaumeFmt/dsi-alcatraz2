package core.view.controller;

import adapters.in.AlcatrazGUIReceiverAdapter;
import core.domain.ClientState;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import core.view.LobbyWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.UUID;

@Slf4j
public class StartGameButtonController implements ActionListener
{
    private final AlcatrazGUIReceiverAdapter guiReceiverAdapter;
    //TODO:wenn der user auf den start button klickt, muss das spiel starten, wenn mindestens zwei spieler in der lobby vorhanden sind

    private LobbyWindow lobbyWindow;
    private ClientPlayer clientPlayer;

    public StartGameButtonController(LobbyWindow lobbyWindow, AlcatrazGUIReceiverAdapter guiReceiverAdapter)
    {
        this.lobbyWindow = lobbyWindow;
        clientPlayer = lobbyWindow.getClientPlayer();
        this.guiReceiverAdapter = guiReceiverAdapter;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        lobbyWindow.getTextPane().setText("You clicked onto the start Button");
        System.out.println(clientPlayer.getPlayerName());
        // TODO: implement logic -> get lobby from ClientState
        if(ClientState.getInstance().getSelectedLobby() != null){
            guiReceiverAdapter.startGame(ClientState.getInstance().getSelectedLobby().getLobbyId());
        } else {
            log.error("No Lobby found in local ClientState {}", ClientState.getInstance());
        }

    }
}

