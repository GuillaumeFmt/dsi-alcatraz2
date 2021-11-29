package adapters.out;

import models.ClientPlayer;
import ports.ClientLobbyHandler;

import java.util.List;

public class ClientLobbyHandlerRMIAdapter implements ClientLobbyHandler {


    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public boolean initGame(List<ClientPlayer> clientPlayerList) {
        return false;

        //Todo implementation
    }


}
