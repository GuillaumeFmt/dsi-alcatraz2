package adapters;

import exceptions.LobbyException;
import models.ClientPlayer;
import models.Lobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface ServerLobbyHandlerRMI extends Remote {

    UUID register (ClientPlayer clientPlayer) throws RemoteException;

    List<Lobby> currentLobbies() throws RemoteException;

    UUID createLobby(String lobbyName, ClientPlayer clientPlayer) throws RemoteException, LobbyException;

    List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) throws RemoteException;

    Boolean leaveLobby(String playerName, UUID lobbyId) throws RemoteException;

    Boolean startGame(Lobby lobby) throws RemoteException;
}
