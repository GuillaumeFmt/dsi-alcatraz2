package core.domain;

import lombok.Getter;
import lombok.Setter;
import models.ClientPlayer;

import java.rmi.registry.Registry;

public class ClientState {

    private static ClientState instance;

    @Getter
    @Setter
    private ClientPlayer localClientPlayer;

    @Getter
    @Setter
    private Registry registry;

    private ClientState() {
        // defeat instantiation
    }

    public static ClientState getInstance() {
        if(instance == null) {
            instance = new ClientState();
        }
        return instance;
    }
}
