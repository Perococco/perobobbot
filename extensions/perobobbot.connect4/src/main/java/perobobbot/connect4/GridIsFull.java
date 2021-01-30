package perobobbot.connect4;

import lombok.NonNull;

public class GridIsFull extends Connect4Exception {


    public GridIsFull(@NonNull TokenType type) {
        super(type+" cannot play, the grid is full");
    }
}
