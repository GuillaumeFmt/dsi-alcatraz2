package adapters.in;

import at.falb.games.alcatraz.api.Alcatraz;
import ports.in.MoveReceiver;

public class AlcatrazGameAdapter {

    private MoveReceiver moveReceiver;
    private Alcatraz alcatraz;

    public AlcatrazGameAdapter(MoveReceiver moveReceiver) {
        this.moveReceiver = moveReceiver;
        this.alcatraz = new Alcatraz();
        alcatraz.init(2, 1);
        alcatraz.showWindow();
        alcatraz.addMoveListener(MoveListenerHandler.create(moveReceiver));
    }
}
