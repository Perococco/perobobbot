package perobobbot.connect4;

import lombok.Getter;
import lombok.NonNull;

public class NotAConnect4 extends Connect4Exception {

    @Getter
    private final @NonNull GridPosition g1;
    @Getter
    private final @NonNull GridPosition g2;

    public NotAConnect4(@NonNull GridPosition g1, @NonNull GridPosition g2) {
        super("Not a winning connect : "+g1+" - "+g2);
        this.g1 = g1;
        this.g2 = g2;
    }
}
