package core;

import adapters.ServerLobbyHandlerRMI;
import adapters.ServerLobbyHandlerRMIStub;
import usecase.LobbyHandlerUseCase;
import usecase.RegistrationUseCase;
import ports.in.LobbyHandler;
import ports.in.Registration;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerInitializer {

    private final int serverPort;
    private final String serverName;
    private Registry registry;

    public ServerInitializer(int serverPort, String serverName) {
        this.serverPort = serverPort;
        this.serverName = serverName;
        try {
            this.registry = LocateRegistry.createRegistry(9876);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void init() {
        registerServerLobbyHandlerStub(new RegistrationUseCase(), new LobbyHandlerUseCase());
        initializeSpreadCommunication();
    }

    private void registerServerLobbyHandlerStub(Registration registration, LobbyHandler lobbyHandler) {
        try {
            ServerLobbyHandlerRMI serverLobbyHandlerStub = (ServerLobbyHandlerRMI) UnicastRemoteObject.exportObject(new ServerLobbyHandlerRMIStub(registration,lobbyHandler), serverPort);
            registry.rebind(serverName, serverLobbyHandlerStub);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void initializeSpreadCommunication(){
        SpreadConnectionManager.instantiateSpreadConnectionManager();
    }
}
