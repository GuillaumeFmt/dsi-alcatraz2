package core;

import models.ClientPlayer;
import ports.in.Registration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RegistrationUseCase implements Registration {

    private final ArrayList<ClientPlayer> registeredPlayers = new ArrayList<>();

    @Override
    public UUID addClientPlayer(ClientPlayer clientPlayer) {
        registeredPlayers.add(clientPlayer);
        registeredPlayers.forEach(player -> System.out.println(player.getPlayerName()));
        return UUID.randomUUID();
    }

    @Override
    public List<ClientPlayer> getClientPlayers() {
        return registeredPlayers;
    }
}

