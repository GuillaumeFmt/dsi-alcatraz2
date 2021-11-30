package ports.in;

import models.ClientPlayer;
import models.Lobby;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface LobbyHandler {

    public UUID createLobby(String lobbyName, ClientPlayer clientPlayer) throws RemoteException;
    public List<Lobby> getCurrentLobbies();
    public List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) throws RemoteException;
    public boolean leaveLobby(String playerName, UUID lobbyId) throws RemoteException;

}
