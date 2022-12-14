package usecase;

import exceptions.ServerNotPrimaryException;
import lombok.extern.slf4j.Slf4j;
import model.LocalServerState;
import models.ClientPlayer;
import ports.in.Registration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class RegistrationUseCase implements Registration {

    @Override
    public UUID addClientPlayer(ClientPlayer clientPlayer) throws ServerNotPrimaryException {
        // check if primary, otherwise throw exception and do not execute the add client command
        if (!LocalServerState.getInstance().amIPrimary()) {
            System.out.println(LocalServerState.getInstance().myServerName);
            System.out.println(LocalServerState.getInstance().actualPrimaryServerName);
            System.out.println("addClientPlayer Request not executed, I'm not primary Server.");
            throw new ServerNotPrimaryException("Cant handle request, I'm not primary Server.");
        }

        ArrayList<ClientPlayer> registeredPlayers = new ArrayList<>();
        registeredPlayers = LocalServerState.getInstance().getRegisteredClientPlayers();
        registeredPlayers.add(clientPlayer);
        registeredPlayers.forEach(player -> log.info("Registered player: {}", player));
        LocalServerState.getInstance().setRegisteredClientPlayers(registeredPlayers);
        ReplicationHandlerUseCase.replicateLocalState(LocalServerState.getInstance());
        return UUID.randomUUID();
    }

    @Override
    public List<ClientPlayer> getClientPlayers() throws ServerNotPrimaryException {
        // check if primary, otherwise throw exception and do not execute the getClientPlayers command
        if (!LocalServerState.getInstance().amIPrimary()) {
            System.out.println("getClientPlayers Request not executed, I'm not primary Server.");
            throw new ServerNotPrimaryException("Cant handle request, I'm not primary Server.");
        }
        return LocalServerState.getInstance().getRegisteredClientPlayers();
    }
}

