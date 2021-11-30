package ports.in;

import exceptions.ServerNotPrimaryException;
import models.ClientPlayer;

import java.rmi.RemoteException;
import java.util.List;
import java.util.UUID;

public interface Registration {
    UUID addClientPlayer(ClientPlayer clientPlayer) throws RemoteException, ServerNotPrimaryException;
    List<ClientPlayer> getClientPlayers() throws RemoteException, ServerNotPrimaryException;
}
