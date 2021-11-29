package core.view;

import adapters.in.AlcatrazGUIReceiverAdapter;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import core.view.controller.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

@Slf4j
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
        createTable(guiReceiverAdapter);
        add(mainPanel);
        this.clientPlayer = clientPLayer;
        addListeners(guiReceiverAdapter);
        this.addRowController = new AddRowController(this);
        setVisible(true);
    }
    public void createTable(AlcatrazGUIReceiverAdapter guiReceiverAdapter)
    {
        tableModel = new DefaultTableModel(
                null,
                new String[]{lobbyIDColumn,lobbyNameColumn,lobbyOwnerColumn,participantsColumn,amountParticipantsColumn,isStartedColumn});

        lobbyTable.setModel(tableModel);

        try {
            guiReceiverAdapter.getLobbies().stream().forEach(lobby -> {
                StringBuilder userList = new StringBuilder();
                lobby.getLobbyParticipants().forEach(participant -> userList.append(clientPlayer.getPlayerName()));
                addRowController.addRow(
                        lobby.getLobbyId(),
                        lobby.getLobbyName(),
                        lobby.getLobbyOwner().getPlayerName(),
                        userList.toString(),
                        lobby.getLobbyParticipants().size(),
                        lobby.isStarted());
            });
        } catch (Exception e) {
            log.error("No lobbies available, exception: " + e.getMessage());
        }
    }
    public void addListeners(AlcatrazGUIReceiverAdapter guiReceiverAdapter)
    {
        createLobbyButton.addActionListener(new CreateLobbyButtonController(this, guiReceiverAdapter));
        joinLobbyButton.addActionListener(new JoinLobbyButtonController(this));
        leaveLobbyButton.addActionListener(new LeaveLobbyButtonController(this));
        startGameButton.addActionListener(new StartGameButtonController(this));
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JPanel getTitlePanel() {
        return titlePanel;
    }

    public void setTitlePanel(JPanel titlePanel) {
        this.titlePanel = titlePanel;
    }

    public JLabel getTitleText() {
        return titleText;
    }

    public void setTitleText(JLabel titleText) {
        this.titleText = titleText;
    }

    public JTable getLobbyTable() {
        return lobbyTable;
    }

    public void setLobbyTable(JTable lobbyTable) {
        this.lobbyTable = lobbyTable;
    }

    public JPanel getLobbyPanel() {
        return lobbyPanel;
    }

    public void setLobbyPanel(JPanel lobbyPanel) {
        this.lobbyPanel = lobbyPanel;
    }

    public JButton getCreateLobbyButton() {
        return createLobbyButton;
    }

    public void setCreateLobbyButton(JButton createLobbyButton) {
        this.createLobbyButton = createLobbyButton;
    }

    public JButton getJoinLobbyButton() {
        return joinLobbyButton;
    }

    public void setJoinLobbyButton(JButton joinLobbyButton) {
        this.joinLobbyButton = joinLobbyButton;
    }

    public JButton getLeaveLobbyButton() {
        return leaveLobbyButton;
    }

    public void setLeaveLobbyButton(JButton leaveLobbyButton) {
        this.leaveLobbyButton = leaveLobbyButton;
    }

    public JButton getStartGameButton() {
        return startGameButton;
    }

    public void setStartGameButton(JButton startGameButton) {
        this.startGameButton = startGameButton;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public void setButtonPanel(JPanel buttonPanel) {
        this.buttonPanel = buttonPanel;
    }

    public JScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public JTextPane getTextPane() {
        return textPane;
    }

    public void setTextPane(JTextPane textPane) {
        this.textPane = textPane;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public String getLobbyIDColumn() {
        return lobbyIDColumn;
    }

    public void setLobbyIDColumn(String lobbyIDColumn) {
        this.lobbyIDColumn = lobbyIDColumn;
    }

    public String getLobbyNameColumn() {
        return lobbyNameColumn;
    }

    public void setLobbyNameColumn(String lobbyNameColumn) {
        this.lobbyNameColumn = lobbyNameColumn;
    }

    public String getLobbyOwnerColumn() {
        return lobbyOwnerColumn;
    }

    public void setLobbyOwnerColumn(String lobbyOwnerColumn) {
        this.lobbyOwnerColumn = lobbyOwnerColumn;
    }

    public String getParticipantsColumn() {
        return participantsColumn;
    }

    public void setParticipantsColumn(String participantsColumn) {
        this.participantsColumn = participantsColumn;
    }

    public String getAmountParticipantsColumn() {
        return amountParticipantsColumn;
    }

    public void setAmountParticipantsColumn(String amountParticipantsColumn) {
        this.amountParticipantsColumn = amountParticipantsColumn;
    }

    public ClientPlayer getClientPlayer() {
        return clientPlayer;
    }

    public AddRowController getAddRowController() {
        return addRowController;
    }

    public String getIsStartedColumn() {
        return isStartedColumn;
    }

    public void setIsStartedColumn(String isStartedColumn) {
        this.isStartedColumn = isStartedColumn;
    }
}
