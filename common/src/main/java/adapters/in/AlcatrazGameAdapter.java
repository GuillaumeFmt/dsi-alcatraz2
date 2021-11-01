package adapters.in;

import ports.in.MoveReceiver;

public class AlcatrazGameAdapter {

    private MoveReceiver moveReceiver;

    public AlcatrazGameAdapter(MoveReceiver moveReceiver) {
        this.moveReceiver = moveReceiver;
    }
}
