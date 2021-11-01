package core;

import adapters.out.ClientMoverRMIAdapter;
import core.usecase.AcknowledgeUseCase;
import models.ClientMove;
import ports.out.ClientMover;
import security.AlcatrazSecurityPolicy;

import java.security.Policy;

public class TestClient {
    public static void main(String[] args) {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        ClientMover clientMover = new ClientMoverRMIAdapter(9876, "Client 2", 9872, "Client 1", new AcknowledgeUseCase());

        System.out.println(clientMover.sendMove(new ClientMove()));
    }
}
