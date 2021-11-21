package adapters;

import models.ClientPlayer;
import models.Lobby;
import ports.in.LobbyHandler;
import ports.in.Registration;

import java.rmi.RemoteException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ServerLobbyHandlerRMIStub implements ServerLobbyHandlerRMI {

    private final Registration registration;
    private LobbyHandler lobbyHandler;

    public ServerLobbyHandlerRMIStub(Registration registration, LobbyHandler lobbyHandler) {
        this.registration = registration;
        this.lobbyHandler = lobbyHandler;
    }

    @Override
    public UUID register(ClientPlayer clientPlayer) throws RemoteException {
        System.out.println("This is: " + clientPlayer.getPlayerName());
        return registration.addClientPlayer(clientPlayer);
    }

    @Override
    public List<Lobby> currentLobbies() throws RemoteException {
        return lobbyHandler.getCurrentLobbies();
    }

    @Override
    public UUID createLobby(String lobbyName, ClientPlayer clientPlayer) throws RemoteException {
        return lobbyHandler.createLobby(lobbyName, clientPlayer);
    }

    @Override
    public List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) throws RemoteException {
        return lobbyHandler.joinLobby(lobby, clientPlayer);
        //return Collections.emptyList();
    }

    @Override
    public Boolean leaveLobby(ClientPlayer clientPlayer) throws RemoteException {
        return lobbyHandler.leavLobby(clientPlayer);
    }

    @Override
    public Boolean startGame(Lobby lobby) throws RemoteException {
        return false;
    }
}
