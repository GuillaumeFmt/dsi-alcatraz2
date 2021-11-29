package core.adapters.in;

import at.falb.games.alcatraz.api.Alcatraz;
import at.falb.games.alcatraz.api.MoveListener;
import models.GameState;
import ports.in.LocalMoveReceiver;

public class AlcatrazGameAdapter {

    private Alcatraz alcatraz;
    private MoveListener moveListener;

    // TODO inject the playId in a more elegant way -> just for demonstration
    public AlcatrazGameAdapter(LocalMoveReceiver localMoveReceiver, int playerId, GameState gameState) {
        this.alcatraz = gameState.getAlcatraz();
        this.alcatraz.init(2, playerId);
        this.alcatraz.showWindow();
        this.moveListener = MoveListenerHandler.create(localMoveReceiver);
        this.alcatraz.addMoveListener(moveListener);
        this.alcatraz.start();
    }
}
