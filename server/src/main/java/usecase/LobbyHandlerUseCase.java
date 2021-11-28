package usecase;

import lombok.extern.slf4j.Slf4j;
import model.LocalServerState;
import models.ClientPlayer;
import models.Lobby;
import ports.in.LobbyHandler;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class LobbyHandlerUseCase implements LobbyHandler {

    // TODO: implement "wait for response behaviour" (after sending spread message to the replicas, we should wait for the ok response -> blocking approach)

    @Override
    public UUID createLobby(String lobbyName, ClientPlayer clientPlayer) throws RemoteException {
        // check if primary, otherwise throw exception and do not execute the create lobby command
        if (!LocalServerState.getInstance().amIPrimary()) {
            System.out.println("createLobby Request not executed, I'm not primary Server.");
            throw new RemoteException("Cant handle request, I'm not primary Server.");
        }


        Lobby lobby = new Lobby(lobbyName, clientPlayer);
        ArrayList<Lobby> lobbyList = LocalServerState.getInstance().getLobbyList();
        lobbyList.add(lobby);
        LocalServerState.getInstance().setLobbyList(lobbyList);
        ReplicationHandlerUseCase.replicateLocalState(LocalServerState.getInstance());
        return lobby.getLobbyId();
    }

    @Override
    public List<Lobby> getCurrentLobbies() {
        return LocalServerState.getInstance().getLobbyList();
    }

    @Override
    public List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer) throws RemoteException {
        // check if primary, otherwise throw exception and do not execute the join lobby command
        if (!LocalServerState.getInstance().amIPrimary()) {
            throw new RemoteException("Cant handle request, I'm not primary Server.");
        }

        ArrayList<ClientPlayer> clientPlayers = new ArrayList<>();
        ArrayList<Lobby> currentLobbies = LocalServerState.getInstance().getLobbyList();
        ArrayList<Lobby> newLobbies = new ArrayList<>();
        boolean lobbyFound = false;

        try {
            for (Lobby currentLobby : currentLobbies) {
                if (currentLobby.getLobbyId().equals(lobby.getLobbyId())) {
                    currentLobby.addLobbyParticipant(clientPlayer);
                    clientPlayers = currentLobby.getLobbyParticipants();
                    log.info("ClientPlayer: {} joined the Lobby: {}", clientPlayer, lobby);
                    newLobbies.add(currentLobby);
                    lobbyFound = true;
                }
                newLobbies.add(currentLobby);
            }

            if (!lobbyFound) {
                throw new Exception();
            }
        } catch (Exception e) {
            log.error("Could not add player to lobby, Lobby {} not found! - Exception: {}", lobby, e.getMessage());
        }

        LocalServerState.getInstance().setLobbyList(newLobbies);
        ReplicationHandlerUseCase.replicateLocalState(LocalServerState.getInstance());
        return clientPlayers;
    }

    @Override
    public boolean leaveLobby(ClientPlayer clientPlayer) throws RemoteException {
        // check if primary, otherwise throw exception and do not execute the leave lobby command
        if (!LocalServerState.getInstance().amIPrimary()) {
            throw new RemoteException("Cant handle request, I'm not primary Server.");
        }

        ArrayList<Lobby> currentLobbies = LocalServerState.getInstance().getLobbyList();
        ArrayList<Lobby> newLobbies = new ArrayList<>();
        boolean playerRemoved = false;

        for (Lobby currentLobby : currentLobbies) {
            ArrayList<ClientPlayer> newClientPlayers = new ArrayList<>();

            for (ClientPlayer currentClientPlayer : currentLobby.getLobbyParticipants()) {
                if (currentClientPlayer.equals(clientPlayer)) {
                    playerRemoved = true;
                    continue;
                } else {
                    newClientPlayers.add(currentClientPlayer);
                }
            }
            currentLobby.setLobbyParticipants(newClientPlayers);
            newLobbies.add(currentLobby);
        }

        System.out.println("Leave lobby operation for " + clientPlayer.toString() + (playerRemoved ? " successful": " not successful -> player not found in lobbies"));

        LocalServerState.getInstance().setLobbyList(newLobbies);
        ReplicationHandlerUseCase.replicateLocalState(LocalServerState.getInstance());
        return playerRemoved;
    }
}
