package usecase;

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
    public UUID addClientPlayer(ClientPlayer clientPlayer) {
        ArrayList<ClientPlayer> registeredPlayers = new ArrayList<>();
        registeredPlayers = LocalServerState.getInstance().getRegisteredClientPlayers();
        registeredPlayers.add(clientPlayer);
        registeredPlayers.forEach(player -> log.info("Registered player: {}", player));
        LocalServerState.getInstance().setRegisteredClientPlayers(registeredPlayers);
        return UUID.randomUUID();
    }

    @Override
    public List<ClientPlayer> getClientPlayers() {
        return LocalServerState.getInstance().getRegisteredClientPlayers();
    }
}

