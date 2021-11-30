package adapters;

import exceptions.LobbyException;
import exceptions.ServerNotPrimaryException;
import exceptions.StartGameException;
import models.ClientPlayer;
import models.Lobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface ServerLobbyHandlerRMI extends Remote {

    UUID register (ClientPlayer clientPlayer) throws RemoteException, ServerNotPrimaryException;

    List<Lobby> currentLobbies() throws RemoteException;

    UUID createLobby(String lobbyName, ClientPlayer clientPlayer) throws RemoteException, LobbyException, ServerNotPrimaryException;

    List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) throws RemoteException, LobbyException, ServerNotPrimaryException;

    Boolean leaveLobby(String playerName, UUID lobbyId) throws RemoteException, ServerNotPrimaryException;

    Boolean startGame(UUID lobbyId) throws RemoteException, LobbyException, StartGameException;
}
