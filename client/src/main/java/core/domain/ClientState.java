package core.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import models.ClientPlayer;
import models.Lobby;

import java.rmi.registry.Registry;
import java.util.ArrayList;

@ToString
public class ClientState {

    private static ClientState instance;

    @Getter
    @Setter
    private ClientPlayer localClientPlayer;

    @Getter
    @Setter
    private Registry registry;

    @Getter @Setter private ArrayList<Lobby> currentLobbies;
    @Getter @Setter private String userName;
    @Getter @Setter private int playerIdxForGame;
    @Getter @Setter private Lobby selectedLobby;

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
