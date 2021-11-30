package adapters.in;

import adapters.ClientLobbyHandlerRMI;
import models.ClientPlayer;

import java.rmi.RemoteException;
import java.util.List;

public class ClientLobbyHandlerRMIStub implements ClientLobbyHandlerRMI {
    @Override
    public boolean isAlive() throws RemoteException {
        // when client is reachable, which is the case when this method can be called simply return true
        return true;
    }

    @Override
    public boolean initGame(List<ClientPlayer> clientPlayerList) throws RemoteException {
        // TODO : start the game locally and initialize communication with participants
        return false;
    }
}
