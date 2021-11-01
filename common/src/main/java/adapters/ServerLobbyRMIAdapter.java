package adapters;

import models.ClientPlayer;
import models.Lobby;
import ports.ServerLobbyHandler;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ServerLobbyRMIAdapter implements ServerLobbyHandler {

    @Override
    public UUID register(ClientPlayer clientPlayer) throws RemoteException {
        //TODO: implement register method
        return null;
    }

    @Override
    public List<Lobby> currentLobbies() throws RemoteException {
        //TODO: return all Lobbies
        return Collections.emptyList();
    }

    @Override
    public Lobby createLobby(String lobbyName, ClientPlayer clientPlayer) throws RemoteException {
        //TODO: create a new Lobby and return Lobby object
        return null;
    }

    @Override
    public List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) throws RemoteException {
        //TODO: implement joinLobby method
        return Collections.emptyList();
    }

    @Override
    public Boolean leaveLobby(ClientPlayer clientPlayer) throws RemoteException {
        //TODO: kick current user from Lobby
        return false;
    }

    @Override
    public Boolean startGame(Lobby lobby) throws RemoteException {
        //TODO: implement start game logic
        return false;
    }
}
