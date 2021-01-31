package perobobbot.connect4.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.Team;

@RequiredArgsConstructor
public class RandomMove extends AbstractPlayer {

    @Getter
    private final @NonNull Team team;

    @Override
    protected int getNextMove(@NonNull Connect4State state) throws InterruptedException {
        return state.pickOneColumn();
    }

}
