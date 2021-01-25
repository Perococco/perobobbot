package perobobbot.connect4;

import lombok.Getter;

public class InvalidColumnIndex extends Connect4Exception {

    @Getter
    private final int columnIndex;

    public InvalidColumnIndex(int columnIndex) {
        super("Column index '"+columnIndex+"' is invalid");
        this.columnIndex = columnIndex;
    }
}
