package perobobbot.connect4.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.TokenType;

@RequiredArgsConstructor
public class RandomMove extends AbstractPlayer {

    public static @NonNull Player.Factory factory() {
        return (team, controller) -> new RandomMove(team);
    }

    @Getter
    private final @NonNull TokenType team;

    @Override
    protected int getNextMove(@NonNull Connect4State state) throws InterruptedException {
//        Thread.sleep(0);
        return state.pickOneColumn();
    }

}
