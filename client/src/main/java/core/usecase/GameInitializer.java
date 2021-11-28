package core.usecase;

import adapters.out.ClientMoverRMI;
import adapters.out.ClientMoverRMIStub;
import exceptions.ClientNotReachableException;
import exceptions.ServerNotPrimaryException;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import models.Lobby;
import ports.ServerLobbyHandler;
import ports.in.RemoteMoveReceiver;
import utils.RetryOnExceptionHandler;

import java.net.ConnectException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

@Slf4j
public class GameInitializer {

    private final ServerLobbyHandler serverLobbyHandler;
    private ClientPlayer myClientPlayer;
    private Registry registry;
    private String[] servers;
    private RetryOnExceptionHandler retryOnExceptionHandler;

    public GameInitializer(String[] servers, ServerLobbyHandler serverLobbyHandler) {
        this.retryOnExceptionHandler = new RetryOnExceptionHandler(3, 2000);
        this.servers = servers;
        this.serverLobbyHandler = serverLobbyHandler;
        rebindServer();
    }

    public void registerUser(String playerName, int port) throws ClientNotReachableException {
        myClientPlayer = new ClientPlayer("  ",port,playerName);
        registerClientMoverStub(new RemoteMoveReceiverUseCase());
        UUID id = null;

        while(true) {
            try {
                id = serverLobbyHandler.register(myClientPlayer);
                log.info("My Player UUID: {}", id);
                break;
            } catch (ServerNotPrimaryException e) {
                log.info("Retrying another server");
                rebindServer();
                registerClientMoverStub(new RemoteMoveReceiverUseCase());
                this.retryOnExceptionHandler.exceptionOccurred();
                continue;
            }
        }
    }

    public void createLobby(String lobbyName) throws ClientNotReachableException {
        UUID id = null;

        while(true) {
            try {
                id = serverLobbyHandler.createLobby(lobbyName, myClientPlayer);
                log.info("My Lobbyy UUID: {}", id);
                break;
            } catch (ServerNotPrimaryException e) {
                log.info("Retrying another server");
                rebindServer();
                registerClientMoverStub(new RemoteMoveReceiverUseCase());
                this.retryOnExceptionHandler.exceptionOccurred();
            }
        }

    }

    public List<Lobby> getCurrentLobbies() throws ClientNotReachableException {
        while(true) {
            try {
                return serverLobbyHandler.currentLobbies();
            } catch (ServerNotPrimaryException e) {
                log.info("Retrying another server");
                rebindServer();
                registerClientMoverStub(new RemoteMoveReceiverUseCase());
                this.retryOnExceptionHandler.exceptionOccurred();
            }
            return Collections.emptyList();
        }
    }

    public void joinLobby(Lobby lobby, ClientPlayer clientPlayer) throws ClientNotReachableException {
        List<ClientPlayer> currentPlayersInLobby = null;
        while (true){
            try {
                currentPlayersInLobby = serverLobbyHandler.joinLobby(lobby, clientPlayer);
                if (!currentPlayersInLobby.isEmpty()) {
                    log.info("Lobby joined by player {}!", clientPlayer);
                    currentPlayersInLobby.forEach(player ->
                            log.info("Player in Lobby: {} - {} - {}",
                                    player.getPlayerName(),
                                    player.getIp(),
                                    player.getPort())
                    );
                }
                break;
            } catch (ServerNotPrimaryException e) {
                log.info("Retrying another server");
                rebindServer();
                registerClientMoverStub(new RemoteMoveReceiverUseCase());
                this.retryOnExceptionHandler.exceptionOccurred();
            }
        }
    }

    public void leaveLobby(ClientPlayer clientPlayer) throws ClientNotReachableException {
        while(true){
            try {
                if (Boolean.TRUE.equals(serverLobbyHandler.leaveLobby(clientPlayer))) {
                    log.info("Player {} left current lobby!", clientPlayer);
                }
            } catch (ServerNotPrimaryException e) {
                log.info("Retrying another server");
                rebindServer();
                registerClientMoverStub(new RemoteMoveReceiverUseCase());
                this.retryOnExceptionHandler.exceptionOccurred();
            }
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

    private void rebindServer(){
        Iterator<String> iterator = Arrays.asList(servers).iterator();
        String server = iterator.next();
        int port = Integer.parseInt(server.split(":")[1]);
        server = server.split(":")[0];

        while(getRegistry(server, port)){
            System.out.println("Trying to bind to new server" + server);
            server = iterator.next();
        }
    }

    private boolean getRegistry(String hostname, int port) {
        try {
            this.registry = LocateRegistry.getRegistry(hostname, port);
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }
}
