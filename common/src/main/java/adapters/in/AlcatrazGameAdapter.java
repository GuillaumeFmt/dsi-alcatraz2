package adapters.in;

import at.falb.games.alcatraz.api.Alcatraz;
import at.falb.games.alcatraz.api.MoveListener;
import ports.in.MoveReceiver;

public class AlcatrazGameAdapter {

    private MoveReceiver moveReceiver;
    private Alcatraz alcatraz;
    private MoveListener moveListener;

    // TODO inject the playId in a more elegant way -> just for demonstration
    public AlcatrazGameAdapter(MoveReceiver moveReceiver, int playerId) {
        this.moveReceiver = moveReceiver;
        this.alcatraz = new Alcatraz();
        alcatraz.init(2, playerId);
        alcatraz.showWindow();
        moveListener = MoveListenerHandler.create(moveReceiver);
        alcatraz.addMoveListener(moveListener);
        alcatraz.start();
    }
}
