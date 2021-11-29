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

        // TODO: implement logic to check if the client is already in a lobby (currently a client can create multible lobbies and joins every lobby he/she creates)

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

        boolean lobbyFound = false;

        ArrayList<Lobby> currentLobbies = LocalServerState.getInstance().getLobbyList();
        Lobby lobbyToAlter = null;
        try {
            for (Lobby currentLobby : currentLobbies) {
                if (currentLobby.getLobbyId().equals(lobby.getLobbyId()))
                    currentLobbies.remove(currentLobby);
                lobbyToAlter = currentLobby;
                lobbyFound = true;
            }
            if (!lobbyFound) {
                throw new Exception();
            }
        } catch (Exception e) {
            log.error("Could not add player to lobby, Lobby {} not found! - Exception: {}", lobby, e.getMessage());
        }

        if(lobbyFound) {
            for (ClientPlayer c : lobbyToAlter.getLobbyParticipants()) {
                if (c.getPlayerName().equals(clientPlayer.getPlayerName())) {
                    log.error("ClientPlayer: {} already in the Lobby: {}", clientPlayer, lobby);
                } else {
                    lobbyToAlter.addLobbyParticipant(clientPlayer);
                    log.info("ClientPlayer: {} joined the Lobby: {}", clientPlayer, lobby);
                }
            }
        }

        currentLobbies.add(lobbyToAlter);
        LocalServerState.getInstance().setLobbyList(currentLobbies);
        ReplicationHandlerUseCase.replicateLocalState(LocalServerState.getInstance());
        return lobbyToAlter.getLobbyParticipants();
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
                if (currentClientPlayer.getPlayerName().equals(clientPlayer.getPlayerName())) {
                    currentLobby.setParticipantCount(currentLobby.getParticipantCount()-1);
                    playerRemoved = true;
                    if(currentLobby.getLobbyOwner().getPlayerName().equals(clientPlayer.getPlayerName()))
                    {
                        ArrayList<ClientPlayer> playerInsideLobby = new ArrayList<>();
                        playerInsideLobby = currentLobby.getLobbyParticipants();
                        for(ClientPlayer clientPlayer1 : playerInsideLobby)
                        {
                            if(!clientPlayer1.getPlayerName().equals(clientPlayer.getPlayerName()))
                            {
                                currentLobby.setLobbyOwner(clientPlayer1);
                                break;
                            }
                        }
                    }
                    continue;
                } else {
                    newClientPlayers.add(currentClientPlayer);
                }
            }
            currentLobby.setLobbyParticipants(newClientPlayers);
            if(!newClientPlayers.isEmpty())
            {
                newLobbies.add(currentLobby);
            }

        }

        System.out.println("Leave lobby operation for " + clientPlayer.toString() + (playerRemoved ? " successful": " not successful -> player not found in lobbies"));

        LocalServerState.getInstance().setLobbyList(newLobbies);
        ReplicationHandlerUseCase.replicateLocalState(LocalServerState.getInstance());
        return playerRemoved;
    }
}
