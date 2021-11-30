package core.view;

import adapters.in.AlcatrazGUIReceiverAdapter;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import core.Client;
import core.domain.ClientState;
import core.view.controller.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import models.Lobby;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.util.ArrayList;
import java.util.Locale;

@Slf4j
@Getter
@Setter
public class LobbyWindow extends JFrame {
    public static int lobbyIDCounter = 0;
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
    private String lobbyIDColumn = "Lobby ID";
    private String lobbyNameColumn = "LobbyName";
    private String lobbyOwnerColumn = "LobbyOwner";
    private String participantsColumn = "Participants";
    private String amountParticipantsColumn = "AmountParticipants";
    private String isStartedColumn = "isStarted";
    private AddRowController addRowController;

    public LobbyWindow(ClientPlayer clientPLayer, AlcatrazGUIReceiverAdapter guiReceiverAdapter) {

        super("AlcatrazLobby");
        setSize(500, 500);
        setLocationRelativeTo(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addRowController = new AddRowController(this);
        this.clientPlayer = clientPLayer;

        //addListeners(guiReceiverAdapter);

        createLobbyButton.addActionListener(new CreateLobbyButtonController(this, guiReceiverAdapter));
        joinLobbyButton.addActionListener(new JoinLobbyButtonController(this, guiReceiverAdapter));
        leaveLobbyButton.addActionListener(new LeaveLobbyButtonController(this, guiReceiverAdapter));
        startGameButton.addActionListener(new StartGameButtonController(this, guiReceiverAdapter));
        startGameButton.setEnabled(false);
        leaveLobbyButton.setEnabled(false);
        add(mainPanel);
        createTable(guiReceiverAdapter);
        setVisible(true);

    }

    public void createTable(AlcatrazGUIReceiverAdapter guiReceiverAdapter) {
        tableModel = new DefaultTableModel(
                null,
                new String[]{lobbyIDColumn, lobbyNameColumn, lobbyOwnerColumn, participantsColumn, amountParticipantsColumn, isStartedColumn});

        lobbyTable.setModel(tableModel);
        lobbyTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int idx = lobbyTable.getSelectedRow();
                if (idx != -1) {
                    ClientState.getInstance().setSelectedLobby(ClientState.getInstance().getCurrentLobbies().get(idx));
                    log.debug("Selected Row : {}", idx);

                }
                if (ClientState.getInstance().getSelectedLobby() != null) {
                    Lobby selectedLobby = ClientState.getInstance().getSelectedLobby();
                    if (selectedLobby.getLobbyParticipants().size() > 1) {
                        startGameButton.setEnabled(true);
                    }
                    log.debug("Selected Lobby Participant Count: {}", selectedLobby.getLobbyParticipants().size());
                    log.debug("Selected Lobby : {}", selectedLobby.getLobbyName());
                }
            }
        });

        lobbyTable.getSelectedRow();
        lobbyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        try {
            ArrayList<Lobby> lobbies = (ArrayList<Lobby>) guiReceiverAdapter.getLobbies();

            ClientState.getInstance().setCurrentLobbies(lobbies);

            lobbies.forEach(lobby -> {
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
            log.error("No lobbies available, exception: " + e.getMessage());
        }
    }

    public void addListeners(AlcatrazGUIReceiverAdapter guiReceiverAdapter) {
        createLobbyButton.addActionListener(new CreateLobbyButtonController(this, guiReceiverAdapter));
        joinLobbyButton.addActionListener(new JoinLobbyButtonController(this, guiReceiverAdapter));
        leaveLobbyButton.addActionListener(new LeaveLobbyButtonController(this, guiReceiverAdapter));
        startGameButton.addActionListener(new StartGameButtonController(this, guiReceiverAdapter));
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(2, 1, new Insets(0, 0, 0, 0), -1, -1));
        titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(titlePanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 1, false));
        titleText = new JLabel();
        Font titleTextFont = this.$$$getFont$$$("Consolas", Font.BOLD, 16, titleText.getFont());
        if (titleTextFont != null) titleText.setFont(titleTextFont);
        titleText.setText("Alcatraz");
        titlePanel.add(titleText, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 1, false));
        lobbyPanel = new JPanel();
        lobbyPanel.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(lobbyPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        scrollPane = new JScrollPane();
        lobbyPanel.add(scrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        lobbyTable = new JTable();
        scrollPane.setViewportView(lobbyTable);
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        lobbyPanel.add(buttonPanel, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        createLobbyButton = new JButton();
        createLobbyButton.setText("Create Lobby");
        buttonPanel.add(createLobbyButton, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        joinLobbyButton = new JButton();
        joinLobbyButton.setText("Join Lobby");
        buttonPanel.add(joinLobbyButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        leaveLobbyButton = new JButton();
        leaveLobbyButton.setText("Leave Lobby");
        buttonPanel.add(leaveLobbyButton, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startGameButton = new JButton();
        startGameButton.setText("Start Game");
        buttonPanel.add(startGameButton, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        textPane = new JTextPane();
        lobbyPanel.add(textPane, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(150, 50), null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}
