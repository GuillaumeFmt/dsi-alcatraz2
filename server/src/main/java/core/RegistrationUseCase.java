package core;

import lombok.extern.slf4j.Slf4j;
import models.ClientPlayer;
import ports.in.Registration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class RegistrationUseCase implements Registration {

    private final ArrayList<ClientPlayer> registeredPlayers = new ArrayList<>();

    @Override
    public UUID addClientPlayer(ClientPlayer clientPlayer) {
        registeredPlayers.add(clientPlayer);
        registeredPlayers.forEach(player -> log.info("Registered player: {}", player));
        return UUID.randomUUID();
    }

    @Override
    public List<ClientPlayer> getClientPlayers() {
        return registeredPlayers;
    }
}

