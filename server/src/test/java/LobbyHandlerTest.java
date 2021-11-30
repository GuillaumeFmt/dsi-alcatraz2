import model.LocalServerState;
import models.ClientPlayer;
import models.Lobby;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import usecase.LobbyHandlerUseCase;
import usecase.ReplicationHandlerUseCase;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LobbyHandlerTest {

    @Mock
    private LocalServerState mockServerState;

    private LobbyHandlerUseCase lobbyHandlerUseCase = new LobbyHandlerUseCase();
    private ArrayList<Lobby> lobbyList;

    @BeforeEach
    void setupTest() {
        lobbyList = new ArrayList<>(List.of(
                new Lobby("lobby1", new ClientPlayer("10.0.0.1", 9876, "player1")),
                new Lobby("lobby2", new ClientPlayer("10.0.0.1", 9876, "player2")),
                new Lobby("lobby3", new ClientPlayer("10.0.0.1", 9876, "player3"))
        ));

        mockServerState.setLobbyList(lobbyList);
    }

    @Test
    void shouldJoinLobby() {
        ClientPlayer clientPlayer = new ClientPlayer("10.0.0.2", 5431, "player4");

        try (
                MockedStatic<LocalServerState> serverState = Mockito.mockStatic(LocalServerState.class);
                MockedStatic<ReplicationHandlerUseCase> replicationHandler = Mockito.mockStatic(ReplicationHandlerUseCase.class);
        ) {
            Assertions.assertDoesNotThrow(() -> {
                serverState.when(LocalServerState::getInstance).thenReturn(mockServerState);
                when(mockServerState.amIPrimary()).thenReturn(true);
                when(mockServerState.getLobbyList()).thenReturn(lobbyList);
                lobbyHandlerUseCase.joinLobby(lobbyList.get(0), clientPlayer);
            });
        }
    }

    @Test
    void shouldNotJoinLobbyWhenInOtherLobby() {
        ClientPlayer clientPlayer = new ClientPlayer("10.0.0.1", 9876, "player1");

        try (
                MockedStatic<LocalServerState> serverState = Mockito.mockStatic(LocalServerState.class);
                MockedStatic<ReplicationHandlerUseCase> ignored = Mockito.mockStatic(ReplicationHandlerUseCase.class);
        ) {
            Assertions.assertThrows(RemoteException.class, () -> {
                serverState.when(LocalServerState::getInstance).thenReturn(mockServerState);
                when(mockServerState.amIPrimary()).thenReturn(true);
                when(mockServerState.getLobbyList()).thenReturn(lobbyList);
                lobbyHandlerUseCase.joinLobby(lobbyList.get(1), clientPlayer);
            });
        }
    }

    @Test
    void shouldNotJoinLobbyWhenInSameLobby() {
        ClientPlayer clientPlayer = new ClientPlayer("10.0.0.1", 9876, "player1");

        try (
                MockedStatic<LocalServerState> serverState = Mockito.mockStatic(LocalServerState.class);
                MockedStatic<ReplicationHandlerUseCase> ignored = Mockito.mockStatic(ReplicationHandlerUseCase.class);
        ) {
            Assertions.assertThrows(RemoteException.class, () -> {
                serverState.when(LocalServerState::getInstance).thenReturn(mockServerState);
                when(mockServerState.amIPrimary()).thenReturn(true);
                when(mockServerState.getLobbyList()).thenReturn(lobbyList);
                lobbyHandlerUseCase.joinLobby(lobbyList.get(0), clientPlayer);
            });
        }
    }

    @Test
    void shouldLeaveLobbyWithoutException() {
        try (
                MockedStatic<LocalServerState> serverState = Mockito.mockStatic(LocalServerState.class);
                MockedStatic<ReplicationHandlerUseCase> ignored = Mockito.mockStatic(ReplicationHandlerUseCase.class);
        ) {
            Assertions.assertDoesNotThrow(() -> {
                serverState.when(LocalServerState::getInstance).thenReturn(mockServerState);
                when(mockServerState.amIPrimary()).thenReturn(true);
                when(mockServerState.getLobbyList()).thenReturn(lobbyList);
                lobbyHandlerUseCase.leaveLobby("player1", lobbyList.get(0).getLobbyId());
            });
        }
    }

    @Test
    void shouldLeaveLobbyAndDeleteEmptyLobby() throws RemoteException {
        try (
                MockedStatic<LocalServerState> serverState = Mockito.mockStatic(LocalServerState.class);
                MockedStatic<ReplicationHandlerUseCase> ignored = Mockito.mockStatic(ReplicationHandlerUseCase.class);
        ) {
            serverState.when(LocalServerState::getInstance).thenReturn(mockServerState);
            when(mockServerState.amIPrimary()).thenReturn(true);
            when(mockServerState.getLobbyList()).thenReturn(lobbyList);
            assertTrue(lobbyHandlerUseCase.leaveLobby("player1", lobbyList.get(0).getLobbyId()));
        }
        assertEquals(2, mockServerState.getLobbyList().size());
    }

    @Test
    void shouldReturnTrueWhenTryingToLeaveBotNotInLobby() throws RemoteException {
        try (
                MockedStatic<LocalServerState> serverState = Mockito.mockStatic(LocalServerState.class);
                MockedStatic<ReplicationHandlerUseCase> ignored = Mockito.mockStatic(ReplicationHandlerUseCase.class);
        ) {
            serverState.when(LocalServerState::getInstance).thenReturn(mockServerState);
            when(mockServerState.amIPrimary()).thenReturn(true);
            when(mockServerState.getLobbyList()).thenReturn(lobbyList);
            assertTrue(lobbyHandlerUseCase.leaveLobby("player2", lobbyList.get(0).getLobbyId()));
        }
    }

    @Test
    void shouldLeaveLobbyAndAssignAnotherLobbyOwner() throws RemoteException {
        ClientPlayer clientPlayer = new ClientPlayer("10.0.0.2", 5431, "player4");
        try (
                MockedStatic<LocalServerState> serverState = Mockito.mockStatic(LocalServerState.class);
                MockedStatic<ReplicationHandlerUseCase> ignored = Mockito.mockStatic(ReplicationHandlerUseCase.class);
        ) {
            serverState.when(LocalServerState::getInstance).thenReturn(mockServerState);
            when(mockServerState.amIPrimary()).thenReturn(true);
            when(mockServerState.getLobbyList()).thenReturn(lobbyList);
            lobbyHandlerUseCase.joinLobby(
                    mockServerState.getLobbyList().get(0), clientPlayer);

            assertTrue(lobbyHandlerUseCase.leaveLobby("player1", lobbyList.get(0).getLobbyId()));
            assertEquals(mockServerState.getLobbyList().get(0).getLobbyOwner().getPlayerName(), clientPlayer.getPlayerName());
        }
    }

}
