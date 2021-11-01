package adapters;

import models.ClientPlayer;
import models.Lobby;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public class ServerLobbyHandlerRMIStub implements ServerLobbyHandlerRMI {
    @Override
    public UUID register(ClientPlayer clientPlayer) throws RemoteException {
        return null;
    }

    @Override
    public List<Lobby> currentLobbies() throws RemoteException {
        return null;
    }

    @Override
    public Lobby createLobby(String lobbyName, ClientPlayer clientPlayer) throws RemoteException {
        return null;
    }

    @Override
    public List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) throws RemoteException {
        return null;
    }

    @Override
    public Boolean leaveLobby(ClientPlayer clientPlayer) throws RemoteException {
        return null;
    }

    @Override
    public Boolean startGame(Lobby lobby) throws RemoteException {
        return null;
    }
}
