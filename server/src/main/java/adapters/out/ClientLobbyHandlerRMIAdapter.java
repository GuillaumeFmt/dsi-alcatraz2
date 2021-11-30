package adapters.out;

import adapters.ClientLobbyHandlerRMI;
import models.ClientPlayer;
import ports.ClientLobbyHandler;

import java.rmi.RemoteException;
import java.util.List;

public class ClientLobbyHandlerRMIAdapter implements ClientLobbyHandler {

    ClientLobbyHandlerRMI clientLobbyHandlerProxy;

    @Override
    public boolean isAlive() throws RemoteException {
        return clientLobbyHandlerProxy.isAlive();
    }

    @Override
    public boolean initGame(List<ClientPlayer> clientPlayerList) throws RemoteException {
        return clientLobbyHandlerProxy.initGame(clientPlayerList);
    }
}
