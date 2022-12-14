package models;

import at.falb.games.alcatraz.api.Alcatraz;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class GameState {
    private static GameState gameStateInstance;
    private Alcatraz alcatraz;

    private GameState() {
        // defeat instantiation
    }

    public static GameState getGameStateInstance() {
        if (gameStateInstance == null) {
            gameStateInstance = new GameState();
        }
        return gameStateInstance;
    }

    public Alcatraz getAlcatraz() {
        if (this.alcatraz == null) {
            this.alcatraz = new Alcatraz();
        }
        return this.alcatraz;
    }
}
