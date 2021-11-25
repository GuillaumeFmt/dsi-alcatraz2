package view.controller;

import view.LobbyWindow;
import view.WelcomeWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SaveButtonController implements ActionListener
{
    //TODO: Bei Eingabe des Usernames soll ein neuer Clientplayer erstellt werden
    //TODO:Bestehende lobbys müssen im Lobbywindow angezeigt werden (in den Konstruktor bzw. beim erstellen der Tabelle rein?)


    private WelcomeWindow welcomeWindow;

    public SaveButtonController(WelcomeWindow welcomeWindow)
    {
        this.welcomeWindow = welcomeWindow;
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        welcomeWindow.setVisible(false);
        LobbyWindow lobby = new LobbyWindow();
    }
}
