package core;

import adapters.in.AlcatrazGameAdapter;
import adapters.out.ClientMoverRMIAdapter;
import core.usecase.AcknowledgeUseCase;
import core.usecase.MoveReceiverUseCase;
import ports.out.ClientMover;
import ports.in.MoveReceiver;
import security.AlcatrazSecurityPolicy;

import java.security.Policy;

public class Client {
    public static void main(String[] args) {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        ClientMover clientMover = new ClientMoverRMIAdapter(9876, "Client 1", 9871, "Client 2", new AcknowledgeUseCase());

        MoveReceiver moveReceiver = new MoveReceiverUseCase(clientMover);

        var alcatrazGame = new AlcatrazGameAdapter(moveReceiver);

        while (true) {

        }
    }
}
