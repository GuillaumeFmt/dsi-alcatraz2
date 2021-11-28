package adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import models.Lobby;
import ports.in.LobbyHandler;
import ports.in.Registration;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class ServerLobbyHandlerRMIStub implements ServerLobbyHandlerRMI {

    private final Registration registration;
    private final LobbyHandler lobbyHandler;

    @Override
    public UUID register(ClientPlayer clientPlayer) throws RemoteException {
        log.info("This is: {}", clientPlayer);
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
    }

    @Override
    public Boolean leaveLobby(ClientPlayer clientPlayer) throws RemoteException {
        return lobbyHandler.leaveLobby(clientPlayer);
    }

    @Override
    public Boolean startGame(Lobby lobby) throws RemoteException {
        return false;
    }
}
