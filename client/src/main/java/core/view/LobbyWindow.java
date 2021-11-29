package core.view;

import adapters.in.AlcatrazGUIReceiverAdapter;
import core.domain.ClientState;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import core.view.controller.*;
import models.GameState;
import models.Lobby;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

@Slf4j
@Getter
@Setter
public class LobbyWindow extends JFrame{
    private JPanel mainPanel;
    private JPanel titlePanel;
    private JLabel titleText;
    private JTable lobbyTable;
    private JPanel lobbyPanel;
    private JButton createLobbyButton;
    private JButton joinLobbyButton;
    private JButton leaveLobbyButton;
    private JButton startGameButton;
    private JPanel buttonPanel;
    private JScrollPane scrollPane;
    private JTextPane textPane;

    private ClientPlayer clientPlayer;

    private DefaultTableModel tableModel;
    private ListSelectionModel selectionModel;

    private String lobbyIDColumn =   "Lobby ID";
    private String lobbyNameColumn=  "LobbyName";
    private String lobbyOwnerColumn = "LobbyOwner";
    private String participantsColumn ="Participants";
    private String amountParticipantsColumn = "AmountParticipants";
    private String isStartedColumn = "isStarted";

    private AddRowController addRowController;

    public static int lobbyIDCounter = 0;

    public LobbyWindow(ClientPlayer clientPLayer, AlcatrazGUIReceiverAdapter guiReceiverAdapter)
    {

        super("AlcatrazLobby");
        setSize(500,500);
        setLocationRelativeTo(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addRowController = new AddRowController(this);
        this.clientPlayer = clientPLayer;
        addListeners(guiReceiverAdapter);
        startGameButton.setEnabled(false);
        leaveLobbyButton.setEnabled(false);
        add(mainPanel);
        createTable(guiReceiverAdapter);
        setVisible(true);
    }
    public void createTable(AlcatrazGUIReceiverAdapter guiReceiverAdapter)
    {
        tableModel = new DefaultTableModel(
                null,
                new String[]{lobbyIDColumn,lobbyNameColumn,lobbyOwnerColumn,participantsColumn,amountParticipantsColumn,isStartedColumn});

        lobbyTable.setModel(tableModel);
        lobbyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        try {
            ArrayList<Lobby> lobbies = (ArrayList<Lobby>) guiReceiverAdapter.getLobbies();

            ClientState.getInstance().setCurrentLobbies(lobbies);

            lobbies.stream().forEach(lobby -> {
                StringBuilder userList = new StringBuilder();
                ArrayList<ClientPlayer> players = lobby.getLobbyParticipants();

                log.debug("Players in the lobby {}", players.toString());
                players.forEach(player -> userList.append(player.getPlayerName()));

                addRowController.addRow(
                        lobby.getLobbyId(),
                        lobby.getLobbyName(),
                        lobby.getLobbyOwner().getPlayerName(),
                        userList.toString(),
                        lobby.getLobbyParticipants().size(),
                        lobby.isStarted());
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.error("No lobbies available, exception: " + e.getMessage());
        }
    }
    public void addListeners(AlcatrazGUIReceiverAdapter guiReceiverAdapter)
    {
        createLobbyButton.addActionListener(new CreateLobbyButtonController(this, guiReceiverAdapter));
        joinLobbyButton.addActionListener(new JoinLobbyButtonController(this, guiReceiverAdapter));
        leaveLobbyButton.addActionListener(new LeaveLobbyButtonController(this, guiReceiverAdapter));
        startGameButton.addActionListener(new StartGameButtonController(this, guiReceiverAdapter));
    }



}
