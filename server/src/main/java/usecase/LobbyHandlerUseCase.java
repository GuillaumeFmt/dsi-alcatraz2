package usecase;

import exceptions.LobbyException;
import lombok.extern.slf4j.Slf4j;
import model.LocalServerState;
import models.ClientPlayer;
import models.Lobby;
import ports.in.LobbyHandler;

import java.rmi.RemoteException;
import java.util.*;

@Slf4j
public class LobbyHandlerUseCase implements LobbyHandler {

    // TODO: implement "wait for response behaviour" (after sending spread message to the replicas, we should wait for the ok response -> blocking approach)

    @Override
    public UUID createLobby(String lobbyName, ClientPlayer clientPlayer) throws RemoteException, LobbyException {
        // check if primary, otherwise throw exception and do not execute the create lobby command
        if (!LocalServerState.getInstance().amIPrimary()) {
            log.info("createLobby Request not executed, I'm not primary Server.");
            throw new RemoteException("Cant handle request, I'm not primary Server.");
        }

        ArrayList<Lobby> currentLobbies = LocalServerState.getInstance().getLobbyList();

        if (currentLobbies.stream().noneMatch(lobby -> lobby.getLobbyName().equals(lobbyName)) && checkPlayerNotInAnyLobby(clientPlayer)) {
            Lobby createLobby = new Lobby(lobbyName, clientPlayer);
            currentLobbies.add(createLobby);
            ReplicationHandlerUseCase.replicateLocalState(LocalServerState.getInstance());
            return createLobby.getLobbyId();
        } else {
            log.error("Player already in lobby {} !", clientPlayer);
            throw new LobbyException(String.format("Player %s already in lobby", clientPlayer.toString()));
        }
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

        ArrayList<Lobby> currentLobbies = LocalServerState.getInstance().getLobbyList();
        Lobby lobbyToJoin = currentLobbies.stream().filter(searchLobby -> searchLobby.getLobbyId().compareTo(lobby.getLobbyId()) == 0)
                .findFirst().orElseThrow(() -> {
                    log.error("Could not add player to lobby, lobby not found! {}", lobby);
                    return new RemoteException("Could not add player to lobby, lobby not found!");
                });

        if (checkPlayerNotInAnyLobby(clientPlayer)) {
            lobbyToJoin.getLobbyParticipants().add(clientPlayer);
            ReplicationHandlerUseCase.replicateLocalState(LocalServerState.getInstance());
        } else {
            log.error("Could not add player to lobby, player {} already in another lobby", clientPlayer);
            throw new RemoteException("Could not add player to lobby, lobby not found!");
        }

        return LocalServerState.getInstance().getLobbyList().stream()
                .filter(searchLobby -> searchLobby.getLobbyId().compareTo(lobby.getLobbyId()) == 0)
                .findFirst().orElseThrow(() -> {
                    log.error("Could not add player to lobby, lobby not found! {}", lobby);
                    return new RemoteException("Could not add player to lobby, lobby not found!");
                }).getLobbyParticipants();
    }

    @Override
    public boolean leaveLobby(String playerName, UUID lobbyId) throws RemoteException {
        if (!LocalServerState.getInstance().amIPrimary()) {
            throw new RemoteException("Cant handle request, I'm not primary Server.");
        }

        ArrayList<Lobby> currentLobbies = LocalServerState.getInstance().getLobbyList();
        if (currentLobbies.stream().filter(lobby -> lobby.getLobbyId().equals(lobbyId)).count() == 1) {
            currentLobbies.stream().filter(lobby -> lobby.getLobbyId().compareTo(lobbyId) == 0).findFirst().ifPresent(foundLobby -> {
                Optional<ClientPlayer> playerToRemoveOptional = foundLobby.getLobbyParticipants().stream().filter(player -> player.getPlayerName().equals(playerName)).findFirst();
                playerToRemoveOptional.ifPresent(playerToRemove -> {
                    foundLobby.getLobbyParticipants().remove(playerToRemove);
                    log.info("Player was removed from lobby {}", playerToRemove);
                });
                if (foundLobby.getLobbyOwner().getPlayerName().equals(playerName) && !foundLobby.getLobbyParticipants().isEmpty()) {
                    foundLobby.setLobbyOwner(foundLobby.getLobbyParticipants().get(0));
                    log.info("Lobby Owner was replaced. Old: {}, New: {}", playerName, foundLobby.getLobbyOwner());
                } else if (foundLobby.getLobbyParticipants().isEmpty()) {
                    currentLobbies.remove(foundLobby);
                }
                ReplicationHandlerUseCase.replicateLocalState(LocalServerState.getInstance());
            });
            Optional<Lobby> concernedLobby = LocalServerState.getInstance().getLobbyList().stream().filter(lobby -> lobby.getLobbyId().compareTo(lobbyId) == 0).findFirst();
            return concernedLobby.map(lobby -> lobby.getLobbyParticipants().stream().noneMatch(clientPlayer -> clientPlayer.getPlayerName().equals(playerName))).orElse(true);
        } else {
            return false;
        }
    }

    private boolean checkPlayerNotInAnyLobby(ClientPlayer clientPlayer) {
        return LocalServerState.getInstance().getLobbyList().stream()
                .flatMap(lobbies -> lobbies.getLobbyParticipants().stream())
                .noneMatch(player -> player.getPlayerName().equals(clientPlayer.getPlayerName()));
    }

}
