package core;

import security.AlcatrazSecurityPolicy;

import java.security.Policy;

/*
* This class needs to stay in this package and may not be renamed because the deployment depends on it and assumes this
* class to be the starting point of the application
* */

public class Server {
    public static void main(String[] args) {

        Policy.setPolicy(new AlcatrazSecurityPolicy());

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        ServerInitializer serverInitializer = new ServerInitializer(9876,"Server");
        serverInitializer.init();

        System.out.println("Server started, initializing .. ");
        String testData = "Some Test Data";
        MessageSender messageSender = new MessageSender();
        messageSender.reliableMulticast(testData);

        while (true) {
            // for debugging...
        }
    }

}





