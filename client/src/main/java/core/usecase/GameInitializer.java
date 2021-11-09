package core.usecase;

import ports.ServerLobbyHandler;

public class GameInitializer {

    private ServerLobbyHandler serverLobbyHandler;

    public GameInitializer(ServerLobbyHandler serverLobbyHandler) {
        this.serverLobbyHandler = serverLobbyHandler;
    }

    public void init() {
        serverLobbyHandler.registerClientMoverStub(new RemoteMoveReceiverUseCase());
    }
}
