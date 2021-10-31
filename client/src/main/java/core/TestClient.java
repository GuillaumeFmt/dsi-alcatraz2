package core;

import adapters.ClientMoverRMIAdapter;
import models.ClientMove;
import ports.ClientMover;
import security.AlcatrazSecurityPolicy;

import java.security.Policy;

public class TestClient {
    public static void main(String[] args) {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        ClientMover clientMover = new ClientMoverRMIAdapter(9876, "Client 2", 9872);

        System.out.println(clientMover.sendMove(new ClientMove()));
    }
}
