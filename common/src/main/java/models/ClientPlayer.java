package models;

import lombok.ToString;

import java.io.Serializable;

@ToString
public class ClientPlayer implements Serializable {

    private final String ip;
    private final int port;
    private final String playerName;

    public ClientPlayer(String ip, int port, String playerName) {
        this.ip = ip;
        this.port = port;
        this.playerName = playerName;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getPlayerName() {
        return playerName;
    }

}
