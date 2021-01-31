package perobobbot.connect4;

import lombok.NonNull;

public class GridIsFull extends Connect4Exception {


    public GridIsFull(@NonNull Team type) {
        super(type+" cannot play, the grid is full");
    }
}
