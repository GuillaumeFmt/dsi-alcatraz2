package core.usecase;

import core.adapters.out.ClientMoverRMI;
import core.adapters.out.ClientMoverRMIStub;
import exceptions.ClientNotReachableException;
import exceptions.ServerNotPrimaryException;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import models.Lobby;
import ports.ServerLobbyHandler;
import ports.in.RemoteMoveReceiver;
import utils.RetryOnExceptionHandler;

import java.net.InetAddress;
import java.net.UnknownHostException;
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

    public GameInitializer(ServerLobbyHandler serverLobbyHandler) {
        this.serverLobbyHandler = serverLobbyHandler;
    }

    public void registerUser(String playerName, int port) {
        try {
            this.registry = LocateRegistry.createRegistry(port);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        //get local ip
        InetAddress myIPAddress;
        String iPAddress = "";
        try {
            myIPAddress = InetAddress.getLocalHost();
            iPAddress = myIPAddress.getHostAddress();
            log.info("My IP address is {}", iPAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        myClientPlayer = new ClientPlayer(iPAddress,port,playerName);
        registerClientMoverStub(new RemoteMoveReceiverUseCase());
        UUID id = null;

        try {
            id = serverLobbyHandler.register(myClientPlayer);

        } catch (ClientNotReachableException e) {
            e.printStackTrace();
        }
        log.info("My Player UUID: {}", id);


    }

    public void createLobby(String lobbyName) {
        UUID id = null;

            try {
                id = serverLobbyHandler.createLobby(lobbyName, myClientPlayer);
                log.info("My Lobbyy UUID: {}", id);

            } catch (ClientNotReachableException e) {
                e.printStackTrace();
            }

    }

    public List<Lobby> getCurrentLobbies() {
        try {
            return serverLobbyHandler.currentLobbies();
        } catch (ClientNotReachableException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public void joinLobby(Lobby lobby, ClientPlayer clientPlayer) {
        List<ClientPlayer> currentPlayersInLobby = null;
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
            } catch (ClientNotReachableException e) {
                log.info("Retrying another server");
            }
    }

    public void leaveLobby(ClientPlayer clientPlayer) {

            try {
                if (Boolean.TRUE.equals(serverLobbyHandler.leaveLobby(clientPlayer))) {
                    log.info("Player {} left current lobby!", clientPlayer);
                }
            } catch (ClientNotReachableException e) {
                log.info("Retrying another server");

            }
    }

    private void registerClientMoverStub(RemoteMoveReceiver remoteMoveReceiver) {
        try {
            ClientMoverRMI clientMoverRMIStub = (ClientMoverRMI) UnicastRemoteObject.exportObject(new ClientMoverRMIStub(remoteMoveReceiver), myClientPlayer.getPort());
            registry.rebind(myClientPlayer.getPlayerName(), clientMoverRMIStub);
            //Naming.rebind("rmi://" + hostname + ":"+ port +"/".concat(myClientPlayer.getPlayerName()), clientMoverRMIStub);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
