package models;

import at.falb.games.alcatraz.api.Player;
import at.falb.games.alcatraz.api.Prisoner;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@ToString
public class ClientMove implements Serializable {
    private int seqNr;
    private Player player;
    private Prisoner prisoner;
    private int rowOrCol;
    private int row;
    private int col;

    public ClientMove(int seqNr, Player player, Prisoner prisoner, int rowOrCol, int row, int col) {
        this.seqNr = seqNr;
        this.player = player;
        this.prisoner = prisoner;
        this.rowOrCol = rowOrCol;
        this.row = row;
        this.col = col;
    }

    public int getSeqNr() {
        return seqNr;
    }

    public Player getPlayer() {
        return player;
    }

    public Prisoner getPrisoner() {
        return prisoner;
    }

    public int getRowOrCol() {
        return rowOrCol;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
