package adapters;

import models.ClientPlayer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientLobbyHandlerRMI extends Remote {

    boolean isAlive() throws RemoteException;

    boolean initGame(List<ClientPlayer> clientPlayerList) throws RemoteException;
}
