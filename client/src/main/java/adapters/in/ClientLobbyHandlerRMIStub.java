package adapters.in;

import adapters.ClientLobbyHandlerRMI;
import core.usecase.ClientLobbyHandlerUseCase;
import lombok.RequiredArgsConstructor;
import models.ClientPlayer;

import java.rmi.RemoteException;
import java.util.List;

@RequiredArgsConstructor
public class ClientLobbyHandlerRMIStub implements ClientLobbyHandlerRMI {

    public final ClientLobbyHandlerUseCase clientLobbyHandlerUseCase;

    @Override
    public boolean isAlive() throws RemoteException {
        return clientLobbyHandlerUseCase.isAlive();
    }

    @Override
    public boolean initGame(List<ClientPlayer> clientPlayerList) throws RemoteException {
        return clientLobbyHandlerUseCase.initGame(clientPlayerList);
    }
}
