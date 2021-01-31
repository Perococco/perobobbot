package perobobbot.connect4.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.Team;

@RequiredArgsConstructor
public class AI extends AbstractPlayer {

    public static Player.Factory factory(int strength) {
        return (team, controller) -> strength<=0?new RandomMove(team):new AI(team, strength);
    }


    @Getter
    private final @NonNull Team team;

    private final Brain brain;

    public AI(@NonNull Team team, int strength) {
        System.out.println("Team:"+team+"  strength:"+strength);
        this.team = team;
        this.brain = new Brain(team,strength);
        this.brain.start();
    }

    @Override
    public void dispose() {
        this.brain.requestStop();
    }

    @Override
    protected int getNextMove(@NonNull Connect4State state) throws InterruptedException {
        return brain.computeNextMove(state);
    }




}
