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
        exitButton.addActionListener(new ActionListener() {@Override public void actionPerformed(ActionEvent e) {System.exit(0);}});}

}
