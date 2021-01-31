package perobobbot.connect4.game;

import lombok.NonNull;
import perobobbot.connect4.Team;
import perobobbot.lang.Looper;

import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.HashMap;
import java.util.Map;

public class Connect4Game extends Looper {

    private final Connect4OverlayController controller;
    private final @NonNull Connect4Grid grid;
    private @NonNull Connect4State state;

    private final @NonNull Player player1;
    private final @NonNull Player player2;
    private Player currentPlayer;

    private final Map<Team,Integer> scores = new HashMap<>();

    public Connect4Game(@NonNull Connect4OverlayController controller,
                        @NonNull Player player1,
                        @NonNull Player player2,
                        @NonNull Connect4Grid grid) {
        this.controller = controller;
        this.player1 = player1;
        this.player2 = player2;
        this.grid = grid;
        this.state = Connect4State.empty(this.grid.getNumberOfRows(), this.grid.getNumberOfColumns());
    }

    @Override
    protected @NonNull IterationCommand performOneIteration() throws Exception {
        this.currentPlayer = getNextPlayer();
        final int move = this.currentPlayer.getPlayerMove(state).toCompletableFuture().get();

        if (move < 0 || move >= this.grid.getNumberOfColumns()) {
            throw new RuntimeException("BUG");
        }

        final var finalPosition = state.getFinalPosition(move);

        grid.addTokenToGrid(this.currentPlayer.getTeam(), move, finalPosition);
        state = state.withPlayAt(this.currentPlayer.getTeam(), move);

        if (!state.hasWinner() && !state.isGridFull()) {
            return IterationCommand.CONTINUE;
        }


        state.getWinningPosition().ifPresent(p -> scores.merge(p.getWinningTeam(), 1, Integer::sum));
        state.getWinningPosition().ifPresentOrElse(controller::setWinner, controller::setDraw);

        System.out.format("%s : %3d   %s : %3d%n",Team.RED, scores.getOrDefault(Team.RED,0),
                           Team.YELLOW, scores.getOrDefault(Team.YELLOW,0));

        sleep(Duration.ofSeconds(5));


        this.grid.reset();
        this.state = Connect4State.empty(this.grid.getNumberOfRows(), this.grid.getNumberOfColumns());
        controller.resetForNewGame();

        return IterationCommand.CONTINUE;
    }

    @Override
    protected void afterLooping() {
        player1.dispose();
        player2.dispose();
    }

    private Player getNextPlayer() {
        return currentPlayer == null || currentPlayer == player2 ? player1 : player2;
    }

    public @NonNull Connect4Grid getGrid() {
        return this.grid;
    }
}
