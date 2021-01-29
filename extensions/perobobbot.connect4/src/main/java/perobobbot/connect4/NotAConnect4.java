package perobobbot.connect4;

import lombok.NonNull;

public class NotAConnect4 extends Connect4Exception {

    private final @NonNull GridPosition g1;
    private final @NonNull GridPosition g2;

    public NotAConnect4(@NonNull GridPosition g1, @NonNull GridPosition g2) {
        super("Not a winning connect : "+g1+" - "+g2);
        this.g1 = g1;
        this.g2 = g2;
    }
}
