package perobobbot.connect4.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.TokenType;

@RequiredArgsConstructor
public class AI extends AbstractPlayer {

    @Getter
    private final @NonNull TokenType team;

    @Override
    protected int getNextMove(@NonNull Connect4State state) throws InterruptedException {
        Thread.sleep(1000);
        return state.pickOneColumn();
    }
}
