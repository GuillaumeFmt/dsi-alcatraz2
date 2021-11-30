package usecase;

import exceptions.LobbyException;
import exceptions.StartGameException;
import model.LocalServerState;
import models.Lobby;
import ports.in.StartGameHandler;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Optional;
import java.util.UUID;

public class StartGameUseCase implements StartGameHandler {
    @Override
    public Boolean startGame(UUID lobbyId) throws StartGameException {
        Optional<Lobby> lobbyOptional = LocalServerState.getInstance().getLobbyList().stream().filter(l -> l.getLobbyId().equals(lobbyId)).findFirst();
        Lobby lobby = lobbyOptional.orElseThrow(() -> new StartGameException("Lobby not found in server state"));

        // TODO: rest davon implementieren
        lobby.getLobbyParticipants().forEach(p -> {
            try {
                Registry registry = LocateRegistry.getRegistry(p.getIp(), p.getPort());
            } catch (RemoteException e) {

            }
        });
        return true;
    }
}
