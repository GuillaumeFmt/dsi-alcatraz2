package usecase;

import adapters.ClientLobbyHandlerRMI;
import exceptions.LobbyException;
import exceptions.StartGameException;
import lombok.extern.slf4j.Slf4j;
import model.LocalServerState;
import models.Lobby;
import ports.ClientLobbyHandler;
import ports.in.StartGameHandler;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class StartGameUseCase implements StartGameHandler {
    @Override
    public Boolean startGame(UUID lobbyId) throws StartGameException {
        Optional<Lobby> lobbyOptional = LocalServerState.getInstance().getLobbyList().stream().filter(l -> l.getLobbyId().equals(lobbyId)).findFirst();
        Lobby lobby = lobbyOptional.orElseThrow(() -> new StartGameException("Lobby not found in server state"));

        // TODO: rest davon implementieren
        lobby.getLobbyParticipants().forEach(p -> {
            try {
                Registry registry = LocateRegistry.getRegistry(p.getIp(), p.getPort());
                ClientLobbyHandlerRMI clientLobbyHandlerProxy = (ClientLobbyHandlerRMI) registry.lookup(p.getPlayerName().concat("Server"));
                if(clientLobbyHandlerProxy.isAlive()){
                    clientLobbyHandlerProxy.initGame(lobby.getLobbyParticipants());
                }
            } catch (RemoteException e) {
                log.error("Could not find registry on client using hostname {}:{}", p.getIp(), p.getPort());
            } catch (NotBoundException e) {
                log.error("Failed to get remote object from client registry at {}:{}", p.getIp(), p.getPort());
            }
        });
        return true;
    }
}
