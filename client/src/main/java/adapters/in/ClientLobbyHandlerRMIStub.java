package adapters.in;

import adapters.ClientLobbyHandlerRMI;
import models.ClientPlayer;

import java.rmi.RemoteException;
import java.util.List;

public class ClientLobbyHandlerRMIStub implements ClientLobbyHandlerRMI {
    @Override
    public boolean isAlive() throws RemoteException {
        return false;
    }

    @Override
    public boolean initGame(List<ClientPlayer> clientPlayerList) throws RemoteException {
        return false;
    }
}
