package core.usecase;

import adapters.in.AlcatrazGameAdapter;
import adapters.out.ClientMoverRMIAdapter;
import core.domain.ClientState;
import models.ClientPlayer;
import models.GameState;
import ports.ClientLobbyHandler;
import ports.in.LocalMoveReceiver;
import ports.out.ClientMover;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ClientLobbyHandlerUseCase implements ClientLobbyHandler {
    @Override
    public boolean isAlive() {
        // when client is reachable, which is the case when this method can be called simply return true
        return true;
    }

    @Override
    public boolean initGame(List<ClientPlayer> clientPlayers) {

        // TODO : Initialize communication with participants

        List<LocalMoveReceiver> localMoveReceivers = new ArrayList<>();

        for (ClientPlayer clientPlayer: clientPlayers) {
            if (clientPlayer.getPlayerName().equals(ClientState.getInstance().getLocalClientPlayer().getPlayerName())) {
                // if I hit myself in the list, I extract the index for the game to be initialized
                ClientState.getInstance().setPlayerIdxForGame(clientPlayers.indexOf(clientPlayer));
                continue;
            }
            ClientMover clientMover = new ClientMoverRMIAdapter(clientPlayer.getPort(), clientPlayer.getPlayerName(), clientPlayer.getIp());
            // clientMover is "Client 2" in this case

            LocalMoveReceiver localMoveReceiver = new LocalMoveReceiverUseCase(clientMover);
            localMoveReceivers.add(localMoveReceiver);

        }

        var alcatrazGame = new AlcatrazGameAdapter(localMoveReceivers, ClientState.getInstance().getPlayerIdxForGame(), GameState.getGameStateInstance(), clientPlayers.size());

        return true;
    }
}
