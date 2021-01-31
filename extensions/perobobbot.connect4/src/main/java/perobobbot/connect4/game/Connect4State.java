package perobobbot.connect4.game;

import lombok.NonNull;
import perobobbot.connect4.GridPosition;
import perobobbot.connect4.Team;

import java.util.Optional;
import java.util.Random;
import java.util.stream.IntStream;

public interface Connect4State {

    static @NonNull Connect4State empty(int nbRows, int nbColumns) {
        return Connect4StateWithArray.empty(nbRows,nbColumns);
    }

    /**
     * @return true if the grid is empty
     */
    boolean isEmpty();

    /**
     * @return true if the grid is full
     */
    boolean isGridFull();

    /**
     * @return an optional containing the last move
     */
    @NonNull Optional<Move> findLastMove();

    /**
     * @return the index of the last column played
     * @throws perobobbot.connect4.GridIsEmpty is no move have been made
     */
    int getColumnIndexOfLastMove();


    /**
     * @return the team of the last played move
     * @throws perobobbot.connect4.GridIsEmpty is no move have been made
     */
    @NonNull Team getTeamOfLastMove();

    /**
     * @return true if the state has a winner
     */
    boolean hasWinner();

    /**
     * @return an optional containing the winning team if any
     */
    @NonNull Optional<Team> getWinningTeam();

    /**
     * @return a stream of the indices of the free column (where a play can play)
     */
    @NonNull IntStream streamIndicesOfFreeColumns();

    /**
     * @param team the team that plays
     * @param columnIndex the index of the column the team played
     * @return a new state base on this with the provided move
     */
    @NonNull Connect4State withPlayAt(@NonNull Team team, int columnIndex);

    /**
     * @param columnIndex a column index
     * @return the final position in the grid of the token if played at the provided column index
     */
    @NonNull GridPosition getFinalPosition(int columnIndex);

    /**
     * @return an optional containing the winning position if any, an empty optional otherwise
     */
    @NonNull Optional<WinningPosition> getWinningPosition();

    /**
     * @return true if only one free column is left, i.e. no choice is available anymore
     */
    boolean onlyOneFreeColumnLeft();

    /**
     * @return a random index among the free colun indices
     */
    int pickOneColumn();

    int pickOneColumn(@NonNull Random random);

    /**
     * @return a copy of the indices of the free columns
     */
    @NonNull int[] getIndicesOfFreeColumns();
}
