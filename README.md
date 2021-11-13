# dsi-alcatraz

# Client
Consists of the alcatraz game library and some RMI functionality
in order to talk to other clients during the game.

# Registration Server
## Spread
The spread daemon runs on multiple servers in the cloud.

dsiars01.westeurope.cloudapp.azure.com
dsiars02.westeurope.cloudapp.azure.com
dsiars03.westeurope.cloudapp.azure.com

After a pull_request or push into remote branches spread-features,
main a build is triggered and the jars are pushed onto those servers.
Afterwards a restart of the application is triggered in order to
use the new jars.

There are two options to log in the servers:

### Option 1
You can put following entries into the `~/.ssh/config` file
```
Host dsiars01
  HostName dsiars01.westeurope.cloudapp.azure.com
  User user01
  IdentityFile ~/.ssh/dsiars01_key.pem

Host dsiars02
  HostName dsiars02.westeurope.cloudapp.azure.com
  User user01
  IdentityFile ~/.ssh/dsiars02_key.pem

Host dsiars03
  HostName dsiars03.westeurope.cloudapp.azure.com
  User user01
  IdentityFile ~/.ssh/dsiars03_key.pem
```  

Also, you need to put each Identity File into the `~/.ssh` directory on your client machine.

### Option 2 - using user and password

### Printing the logs into the console

The logs can be observed by ssh'ing into the servers.
This could look something like this:

```bash
# option 1
ssh dsiars01 "journalctl -u alcatraz.service -f"
ssh dsiars02 "journalctl -u alcatraz.service -f"
ssh dsiars03 "journalctl -u alcatraz.service -f"

ssh dsiars01 "journalctl -u spread.service -f"
ssh dsiars02 "journalctl -u spread.service -f"
ssh dsiars03 "journalctl -u spread.service -f"

# option 2 - the password is $BxfkoGwDuAQTWT8
ssh user01@dsiars01.westeurope.cloudapp.azure.com "journalctl -u alcatraz.service -f"
ssh user01@dsiars02.westeurope.cloudapp.azure.com "journalctl -u alcatraz.service -f"
ssh user01@dsiars03.westeurope.cloudapp.azure.com "journalctl -u alcatraz.service -f"

ssh user01@dsiars01.westeurope.cloudapp.azure.com "journalctl -u spread.service -f"
ssh user01@dsiars02.westeurope.cloudapp.azure.com "journalctl -u spread.service -f"
ssh user01@dsiars03.westeurope.cloudapp.azure.com "journalctl -u spread.service -f"
```


