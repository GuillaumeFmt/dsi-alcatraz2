package adapters.in;

import ports.in.AlcatrazGUIReceiver;

public class AlcatrazGUIReceiverAdapter {

    private final AlcatrazGUIReceiver alcatrazGUIReceiver;

    public AlcatrazGUIReceiverAdapter(AlcatrazGUIReceiver alcatrazGUIReceiver) {
        this.alcatrazGUIReceiver = alcatrazGUIReceiver;
    }

    public void createUser(String userName, int port) {
        alcatrazGUIReceiver.createUser(userName, port);
    }

}
