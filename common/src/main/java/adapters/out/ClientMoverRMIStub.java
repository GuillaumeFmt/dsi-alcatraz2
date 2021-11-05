package adapters.out;

import models.ClientMove;
import models.ClientPlayer;
import ports.in.ClientAcknowledge;

import java.rmi.RemoteException;

public class ClientMoverRMIStub implements ClientMoverRMI {

    private final ClientAcknowledge clientAcknowledge;

    public ClientMoverRMIStub(ClientAcknowledge clientAcknowledge) {
        this.clientAcknowledge = clientAcknowledge;
    }

    @Override
    public boolean sendMove(ClientMove clientMove) throws RemoteException {
        System.out.println("received a move"); // TODO just for demonstration -> remove
        return clientAcknowledge.sendAcknowledge();
    }

    @Override
    public boolean nextPlayerMove(ClientPlayer clientPlayer) throws RemoteException {
        return false;
    }
}
