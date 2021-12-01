package model;

import core.PrimaryServerHandler;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import models.Lobby;

import java.io.Serializable;
import java.util.ArrayList;

@Slf4j
public class LocalServerState implements Serializable {

    ArrayList<Lobby> lobbyList;
    ArrayList<ClientPlayer> registeredClientPlayers;
    public String actualPrimaryServerName;
    public String myServerName;

    private LocalServerState() {
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

    public String getMyServerName() {
        return myServerName;
    }

    public void setMyServerName(String myServerName) {
        log.debug("Set Servername {}", myServerName);
        this.myServerName = myServerName;
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
        String oldServerName = localServerStateInstance.getMyServerName();

        localServerStateInstance = localServerState;
        localServerStateInstance.setMyServerName(oldServerName);
    }

    public boolean amIPrimary() {
        if (actualPrimaryServerName != null) {
            log.debug("MyServerName {} - actual PrimaryServerName {} - compare {} ", myServerName, actualPrimaryServerName, myServerName.equals(actualPrimaryServerName ));
            return myServerName.equals(actualPrimaryServerName);
        } else {
            return false;
        }
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
