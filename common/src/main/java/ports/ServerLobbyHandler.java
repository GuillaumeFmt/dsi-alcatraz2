package ports;

import adapters.out.ClientMoverRMI;
import models.ClientPlayer;
import models.Lobby;
import ports.in.ClientAcknowledge;

import java.util.List;
import java.util.UUID;

public interface ServerLobbyHandler {

    UUID register (ClientPlayer clientPlayer);

    List<Lobby> currentLobbies() ;

    Lobby createLobby(String lobbyName, ClientPlayer clientPlayer);

    List<ClientPlayer> joinLobby(Lobby lobby, ClientPlayer clientPlayer);

    Boolean leaveLobby(ClientPlayer clientPlayer);

    Boolean startGame(Lobby lobby);

    public void registerClientMoverStub(ClientAcknowledge clientAcknowledge);

    public ClientMoverRMI getClientMoverProxy();

}
