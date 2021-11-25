package view;

import view.controller.SaveButtonController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeWindow extends JFrame
{
    private JLabel nameText;
    private JTextField playerName;
    private JButton saveButton;
    private JButton exitButton;
    private JPanel mainPanel;


    public WelcomeWindow()
    {
        setTitle("Shawshank Redemption");
        setSize(500,500);
        setLocationRelativeTo(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(mainPanel);
        addActionListeners();
        setVisible(true);
    }

    public void addActionListeners()
    {
        saveButton.addActionListener(new SaveButtonController(this));
        exitButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {System.exit(0);}});
    }

    public JLabel getNameText() {
        return nameText;
    }

    public void setNameText(JLabel nameText) {
        this.nameText = nameText;
    }

    public JTextField getPlayerName() {
        return playerName;
    }

    public void setPlayerName(JTextField playerName) {
        this.playerName = playerName;
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public void setSaveButton(JButton saveButton) {
        this.saveButton = saveButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public void setExitButton(JButton exitButton) {
        this.exitButton = exitButton;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }
}
