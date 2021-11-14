package models;

public class ClientPlayer {

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
