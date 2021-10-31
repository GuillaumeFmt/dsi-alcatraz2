package core;

import java.io.FilePermission;
import java.net.SocketPermission;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Policy;
import java.util.PropertyPermission;

public class AlcatrazSecurityPolicy extends Policy {

        private static PermissionCollection perms;

        public AlcatrazSecurityPolicy() {
            super();
            if (perms == null) {
                perms = new AlcatrazPermissionCollection();
                addPermissions();
            }
        }

        @Override
        public PermissionCollection getPermissions(CodeSource codesource) {
            return perms;
        }

        private void addPermissions() {
            SocketPermission socketPermission = new SocketPermission("localhost:9871-9876", "connect, resolve");
            PropertyPermission propertyPermission = new PropertyPermission("*", "read, write");
            FilePermission filePermission = new FilePermission("<<ALL FILES>>", "read");

            perms.add(socketPermission);
            perms.add(propertyPermission);
            perms.add(filePermission);
        }
}
