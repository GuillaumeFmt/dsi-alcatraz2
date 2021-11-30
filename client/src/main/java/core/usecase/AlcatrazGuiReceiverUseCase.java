package core.usecase;

import adapters.out.ClientMoverRMI;
import adapters.out.ClientMoverRMIStub;
import core.domain.ClientState;
import exceptions.ClientNotReachableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import models.Lobby;
import ports.ServerLobbyHandler;
import ports.in.AlcatrazGUIReceiver;
import ports.in.RemoteMoveReceiver;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class AlcatrazGuiReceiverUseCase implements AlcatrazGUIReceiver {

    private final ServerLobbyHandler serverLobbyHandler;

    @Override
    public void createUser(String userName, int port) {
        log.info("Username: {}, Port: {}", userName, port);
        try {
            ClientState.getInstance().setRegistry(LocateRegistry.createRegistry(port));
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
        ClientState.getInstance().setLocalClientPlayer(new ClientPlayer(iPAddress, port, userName));
        registerClientMoverStub(new RemoteMoveReceiverUseCase());

        try {
            UUID id = serverLobbyHandler.register(ClientState.getInstance().getLocalClientPlayer());
            log.info("My Player UUID: {}", id);

        } catch (ClientNotReachableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Lobby createLobby(String lobbyName) {
        try {
            UUID id = serverLobbyHandler.createLobby(lobbyName, ClientState.getInstance().getLocalClientPlayer());
            log.info("My Lobby UUID: {}", id);

        } catch (ClientNotReachableException e) {
            log.error("Create Lobby caused exception: {0}", e);
        }
        return null;
    }

    @Override
    public Lobby joinLobby(Lobby lobby, ClientPlayer clientPlayer) {
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
        return lobby;
    }

    @Override
    public Boolean leaveLobby(UUID lobbyId) {
        try {
            if (serverLobbyHandler.leaveLobby(ClientState.getInstance().getLocalClientPlayer().getPlayerName(), lobbyId)) {
                log.info("Player {} left current lobby!", ClientState.getInstance().getLocalClientPlayer());
                return true;
            }
        } catch (ClientNotReachableException e) {
            log.info("Retrying another server");
        }
        return false;
    }

    @Override
    public List<Lobby> getLobbies() {
        try {
            return serverLobbyHandler.currentLobbies();
        } catch (ClientNotReachableException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private void registerClientMoverStub(RemoteMoveReceiver remoteMoveReceiver) {
        try {
            ClientMoverRMI clientMoverRMIStub = (ClientMoverRMI) UnicastRemoteObject.exportObject(new ClientMoverRMIStub(remoteMoveReceiver), ClientState.getInstance().getLocalClientPlayer().getPort());
            ClientState.getInstance().getRegistry().rebind(ClientState.getInstance().getLocalClientPlayer().getPlayerName(), clientMoverRMIStub);
            //Naming.rebind("rmi://" + hostname + ":"+ port +"/".concat(myClientPlayer.getPlayerName()), clientMoverRMIStub);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
