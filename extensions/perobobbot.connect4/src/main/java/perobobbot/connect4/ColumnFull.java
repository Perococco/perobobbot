package perobobbot.connect4;

import lombok.Getter;

public class ColumnFull extends Connect4Exception{

    @Getter
    private final int columnIdx;

    public ColumnFull(int columnIdx) {
        super("Cannot play in column "+columnIdx+". It is full!");
        this.columnIdx = columnIdx;
    }
}
