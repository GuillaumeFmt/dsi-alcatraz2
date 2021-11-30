package ports.in;

import at.falb.games.alcatraz.api.Alcatraz;

public interface GameState {

    public models.GameState getGameStateInstance();

    public void setGameStateInstance(Alcatraz alcatraz);

}
