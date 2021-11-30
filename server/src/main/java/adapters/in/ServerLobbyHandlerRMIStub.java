package adapters.in;

import adapters.ServerLobbyHandlerRMI;
import exceptions.LobbyException;
import exceptions.ServerNotPrimaryException;
import exceptions.StartGameException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import models.Lobby;
import ports.in.LobbyHandler;
import ports.in.Registration;
import ports.in.StartGameHandler;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class ServerLobbyHandlerRMIStub implements ServerLobbyHandlerRMI {

    private final Registration registration;
    private final LobbyHandler lobbyHandler;
    private final StartGameHandler startGameHandler;


    @Override
    public UUID register(ClientPlayer clientPlayer) throws RemoteException, ServerNotPrimaryException {
        log.info("This is: {}", clientPlayer);
        return registration.addClientPlayer(clientPlayer);
    }

    @Override
    public List<Lobby> currentLobbies() throws RemoteException {
        return lobbyHandler.getCurrentLobbies();
    }

    @Override
    public UUID createLobby(String lobbyName, ClientPlayer clientPlayer) throws RemoteException, LobbyException, ServerNotPrimaryException {
        return lobbyHandler.createLobby(lobbyName, clientPlayer);
    }

    @Override
    public List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) throws RemoteException, LobbyException, ServerNotPrimaryException {
        return lobbyHandler.joinLobby(lobby, clientPlayer);
    }

    @Override
    public Boolean leaveLobby(String playerName, UUID lobbyId) throws RemoteException, ServerNotPrimaryException {
        return lobbyHandler.leaveLobby(playerName, lobbyId);
    }

    @Override
    public Boolean startGame(UUID lobbyId) throws RemoteException, LobbyException, StartGameException {
        return startGameHandler.startGame(lobbyId);
    }
}
