package core.usecase;

import adapters.ClientLobbyHandlerRMI;
import adapters.in.ClientLobbyHandlerRMIStub;
import adapters.out.ClientMoverRMI;
import adapters.out.ClientMoverRMIStub;
import core.domain.ClientState;
import exceptions.StartGameException;
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
        //TODO: InetAddress gives back IP address of any of your local NICs. But we need to get one for the server reachable IP address
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
        registerClientLobbyHandlerStub(new ClientLobbyHandlerUseCase());
        UUID id = serverLobbyHandler.register(ClientState.getInstance().getLocalClientPlayer());
        log.info("My Player UUID: {}", id);
    }

    @Override
    public Lobby createLobby(String lobbyName) {
        UUID id = serverLobbyHandler.createLobby(lobbyName, ClientState.getInstance().getLocalClientPlayer());
        log.info("My Lobby UUID: {}", id);
        return null;
    }

    @Override
    public Lobby joinLobby(Lobby lobby, ClientPlayer clientPlayer) {
        List<ClientPlayer> currentPlayersInLobby = null;
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
        return lobby;
    }

    @Override
    public Boolean leaveLobby(UUID lobbyId) {
        if (serverLobbyHandler.leaveLobby(ClientState.getInstance().getLocalClientPlayer().getPlayerName(), lobbyId)) {
            log.info("Player {} left current lobby!", ClientState.getInstance().getLocalClientPlayer());
            return true;
        }
        return false;
    }

    @Override
    public List<Lobby> getLobbies() {
        return serverLobbyHandler.currentLobbies();
    }

    @Override
    public Boolean startGame(UUID lobbyId) {
        try {
            return serverLobbyHandler.startGame(lobbyId);
        } catch (StartGameException e) {
            log.error("Could not start the game {}", e.toString());
        }
        return false;
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

    private void registerClientLobbyHandlerStub(ClientLobbyHandlerUseCase clientLobbyHandlerUseCase) {
        try {
            ClientLobbyHandlerRMI clientLobbyHandlerRMIStub = (ClientLobbyHandlerRMI) UnicastRemoteObject.exportObject(new ClientLobbyHandlerRMIStub(clientLobbyHandlerUseCase), ClientState.getInstance().getLocalClientPlayer().getPort());
            //TODO: find a better way to name the remote object, so that server does not need to concat hardcoded
            ClientState.getInstance().getRegistry().rebind(ClientState.getInstance().getLocalClientPlayer().getPlayerName().concat("Server"), clientLobbyHandlerRMIStub);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
