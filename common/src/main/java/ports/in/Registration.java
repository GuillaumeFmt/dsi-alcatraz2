package ports.in;

import models.ClientPlayer;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface Registration {

    public UUID addClientPlayer(ClientPlayer clientPlayer) throws RemoteException;
    public List<ClientPlayer> getClientPlayers() throws RemoteException;
}
