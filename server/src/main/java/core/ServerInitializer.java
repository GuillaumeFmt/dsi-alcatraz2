package core;

import adapters.ServerLobbyHandlerRMI;
import adapters.in.ServerLobbyHandlerRMIStub;
import ports.in.StartGameHandler;
import usecase.LobbyHandlerUseCase;
import usecase.RegistrationUseCase;
import ports.in.LobbyHandler;
import ports.in.Registration;
import usecase.StartGameUseCase;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerInitializer {

    private static ServerInitializer instance;

    private static final int SERVER_PORT = 9876;
    private static final String SERVER_NAME = "RegistrationServer";
    private static Registry registry;
    private static ServerLobbyHandlerRMI serverLobbyHandlerRMI;

    private ServerInitializer() {
        // defeat instantiation
    }

    public static ServerInitializer getInstance() {
        if (instance == null) {
            instance = new ServerInitializer();
        }
        return instance;
    }

    public void init() {
        initializeRegistry();
        registerServerLobbyHandlerStub(new RegistrationUseCase(), new LobbyHandlerUseCase(), new StartGameUseCase());
        initializeSpreadCommunication();
    }

    private static void initializeRegistry() {
        try {
            ServerInitializer.registry = LocateRegistry.createRegistry(9876);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void registerServerLobbyHandlerStub(Registration registration, LobbyHandler lobbyHandler, StartGameHandler startGameHandler) {
        try {
            ServerInitializer.serverLobbyHandlerRMI = new ServerLobbyHandlerRMIStub(registration,lobbyHandler, startGameHandler);
            final ServerLobbyHandlerRMI serverLobbyHandlerStub = ServerInitializer.serverLobbyHandlerRMI;
            ServerLobbyHandlerRMI remote = (ServerLobbyHandlerRMI) UnicastRemoteObject.exportObject(serverLobbyHandlerStub, ServerInitializer.SERVER_PORT);
            ServerInitializer.registry.rebind(SERVER_NAME, remote);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void initializeSpreadCommunication(){
        SpreadConnectionManager.instantiateSpreadConnectionManager();
    }
}
