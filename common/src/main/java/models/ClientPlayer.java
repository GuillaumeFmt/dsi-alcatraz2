package models;

import at.falb.games.alcatraz.api.Player;

public class ClientPlayer {

    private String ip;
    private int port;
    private Player player;

    public ClientPlayer(String ip, int port, Player player) {
        this.ip = ip;
        this.port = port;
        this.player = player;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public Player getPlayer() {
        return player;
    }

}
