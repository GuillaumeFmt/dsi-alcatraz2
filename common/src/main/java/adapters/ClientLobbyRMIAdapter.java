package adapters;

import models.ClientPlayer;
import ports.ClientLobbyHandler;

import java.rmi.RemoteException;
import java.util.List;

public class ClientLobbyRMIAdapter implements ClientLobbyHandler {


    @Override
    public boolean isAlive() throws RemoteException {
        return true;
    }

    @Override
    public boolean initGame(List<ClientPlayer> clientPlayerList) throws RemoteException {
        return false;

        //Todo implementation
    }


}
