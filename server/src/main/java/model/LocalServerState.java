package model;

import core.PrimaryServerHandler;
import models.ClientPlayer;
import models.Lobby;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocalServerState implements Serializable {

    ArrayList<Lobby> lobbyList;
    ArrayList<ClientPlayer> registeredClientPlayers;
    public String actualPrimaryServerName;

    public LocalServerState() {
        this.lobbyList = new ArrayList<Lobby>();
        this.registeredClientPlayers = new ArrayList<ClientPlayer>();
        this.actualPrimaryServerName = PrimaryServerHandler.getInstance().actualPrimaryServerName;
    }

    public ArrayList<Lobby> getLobbyList() {
        return lobbyList;
    }

    public void setLobbyList(ArrayList<Lobby> lobbyList) {
        this.lobbyList = lobbyList;
    }

    public ArrayList<ClientPlayer> getRegisteredClientPlayers() {
        return registeredClientPlayers;
    }

    public void setRegisteredClientPlayers(ArrayList<ClientPlayer> registeredClientPlayers) {
        this.registeredClientPlayers = registeredClientPlayers;
    }

    private static LocalServerState localServerStateInstance;

    public static LocalServerState getInstance() {
        if (localServerStateInstance == null) {
            localServerStateInstance = new LocalServerState();
        }
        return localServerStateInstance;
    }

    public void setPrimary(String primary) {
        this.actualPrimaryServerName = primary;
    }

    public static void setLocalServerState(LocalServerState localServerState) {
        localServerStateInstance = localServerState;
    }

    @Override
    public String toString() {
        return "LocalServerState{" +
                "lobbyList=" + lobbyList +
                ", registeredClientPlayers=" + registeredClientPlayers +
                ", actualPrimaryServerName='" + actualPrimaryServerName + '\'' +
                '}';
    }
}
