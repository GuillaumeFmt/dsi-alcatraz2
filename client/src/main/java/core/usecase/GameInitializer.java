package core.usecase;

import adapters.out.ClientMoverRMI;
import adapters.out.ClientMoverRMIStub;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import models.Lobby;
import ports.ServerLobbyHandler;
import ports.in.RemoteMoveReceiver;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.UUID;

@Slf4j
public class GameInitializer {

    private final ServerLobbyHandler serverLobbyHandler;
    private final ClientPlayer myClientPlayer;
    private Registry registry;

    public GameInitializer(int serverPort, ServerLobbyHandler serverLobbyHandler, ClientPlayer clientPlayer) {
        this.serverLobbyHandler = serverLobbyHandler;
        this.myClientPlayer = clientPlayer;
        try {
            this.registry = LocateRegistry.getRegistry(serverPort);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void init() {
        registerClientMoverStub(new RemoteMoveReceiverUseCase());
        UUID id = serverLobbyHandler.register(myClientPlayer);
        log.info("My Player UUID: {}", id);
    }

    public void createLobby(String lobbyName) {
        UUID id = serverLobbyHandler.createLobby(lobbyName, myClientPlayer);
        log.info("My Lobbyy UUID: {}", id);
    }

    public List<Lobby> getCurrentLobbies() {
        return serverLobbyHandler.currentLobbies();
    }

    public void joinLobby(Lobby lobby, ClientPlayer clientPlayer) {
        List<ClientPlayer> currentPlayersInLobby = serverLobbyHandler.joinLobby(lobby, clientPlayer);
        if (!currentPlayersInLobby.isEmpty()) {
            log.info("Lobby joined by player {}!", clientPlayer);

        }
        currentPlayersInLobby.forEach(player ->
                log.info("Player in Lobby: {} - {} - {}",
                        player.getPlayerName(),
                        player.getIp(),
                        player.getPort())
        );
    }

    public void leaveLobby(ClientPlayer clientPlayer) {
        if (Boolean.TRUE.equals(serverLobbyHandler.leaveLobby(clientPlayer))) {
            log.info("Player {} left current lobby!", clientPlayer);
        }
    }

    private void registerClientMoverStub(RemoteMoveReceiver remoteMoveReceiver) {
        try {
            ClientMoverRMI clientMoverRMIStub = (ClientMoverRMI) UnicastRemoteObject.exportObject(new ClientMoverRMIStub(remoteMoveReceiver), myClientPlayer.getPort());
            registry.rebind(myClientPlayer.getPlayerName(), clientMoverRMIStub);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
