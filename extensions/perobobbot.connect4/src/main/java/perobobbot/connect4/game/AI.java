package perobobbot.connect4.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.Node;
import perobobbot.connect4.TokenType;
import perobobbot.lang.Looper;
import perobobbot.lang.Todo;
import perobobbot.lang.fp.Either;
import perobobbot.lang.fp.Value2;

import java.util.concurrent.SynchronousQueue;

@RequiredArgsConstructor
public class AI extends AbstractPlayer {

    public static Player.Factory factory(int strength) {
        return (team, controller) -> strength<=0?new RandomMove(team):new AI(team, strength);
    }


    @Getter
    private final @NonNull TokenType team;

    private final int strength;

    @Override
    protected int getNextMove(@NonNull Connect4State state) throws InterruptedException {
        return state.pickOneColumn();
    }




}
