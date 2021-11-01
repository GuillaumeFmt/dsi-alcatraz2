package adapters;

import models.ClientPlayer;
import models.Lobby;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface ServerLobbyHandlerRMI extends Remote {

    UUID register (ClientPlayer clientPlayer) throws RemoteException;

    List<Lobby> currentLobbies() throws RemoteException;

    Lobby createLobby(String lobbyName, ClientPlayer clientPlayer) throws RemoteException;

    List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) throws RemoteException;

    Boolean leaveLobby(ClientPlayer clientPlayer) throws RemoteException;

    Boolean startGame(Lobby lobby) throws RemoteException;
}
