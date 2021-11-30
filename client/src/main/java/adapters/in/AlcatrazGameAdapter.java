package adapters.in;

import at.falb.games.alcatraz.api.Alcatraz;
import at.falb.games.alcatraz.api.MoveListener;
import models.GameState;
import ports.in.LocalMoveReceiver;

import java.util.List;

public class AlcatrazGameAdapter {

    private Alcatraz alcatraz;
    private MoveListener moveListener;

    // TODO inject the playId in a more elegant way -> just for demonstration
    public AlcatrazGameAdapter(List<LocalMoveReceiver> localMoveReceivers, int playerId, GameState gameState, int numPlayers) {
        this.alcatraz = gameState.getAlcatraz();
        this.alcatraz.init(numPlayers, playerId);
        this.alcatraz.showWindow();

        localMoveReceivers.forEach(localMoveReceiver -> {
            this.moveListener = MoveListenerHandler.create(localMoveReceiver);
            this.alcatraz.addMoveListener(moveListener);
        });

        this.alcatraz.start();
    }
}
