package ports;

import models.ClientPlayer;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientLobbyHandler {

    boolean isAlive() throws RemoteException;

    boolean initGame(List<ClientPlayer> clientPlayerList) throws RemoteException;
}
