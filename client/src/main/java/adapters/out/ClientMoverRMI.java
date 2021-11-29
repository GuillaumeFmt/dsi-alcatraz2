package adapters.out;

import models.ClientMove;
import models.ClientPlayer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientMoverRMI extends Remote {

    public boolean sendMove(ClientMove clientMove) throws RemoteException;

    public boolean nextPlayerMove(ClientPlayer clientPlayer) throws RemoteException;
}
