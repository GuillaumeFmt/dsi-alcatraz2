package ports.in;

import exceptions.LobbyException;
import exceptions.ServerNotPrimaryException;
import models.ClientPlayer;
import models.Lobby;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;
import java.util.List;
import java.util.UUID;

public interface LobbyHandler {

    public UUID createLobby(String lobbyName, ClientPlayer clientPlayer) throws RemoteException, LobbyException, ServerNotPrimaryException;
    public List<Lobby> getCurrentLobbies();
    public List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) throws RemoteException, ServerNotPrimaryException, LobbyException;
    public boolean leaveLobby(String playerName, UUID lobbyId) throws RemoteException, ServerNotPrimaryException;

}
