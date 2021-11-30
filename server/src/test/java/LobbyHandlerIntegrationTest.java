import model.LocalServerState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

public class LobbyHandlerIntegrationTest {
    public LocalServerState serverState = new LocalServerState();

    @Test
    void smokeTest() {
        Assertions.assertEquals(Collections.emptyList(), serverState.getLobbyList());
    }


}
