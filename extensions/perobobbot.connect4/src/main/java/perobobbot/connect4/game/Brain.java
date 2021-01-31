package perobobbot.connect4.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.connect4.Connect4Constants;
import perobobbot.connect4.Team;
import perobobbot.lang.ExchangePoint;
import perobobbot.lang.Looper;
import perobobbot.lang.ThrowableTool;
import perobobbot.lang.fp.Function1;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Log4j2
@RequiredArgsConstructor
public class Brain extends Looper {

    public static final int SCORE_FOR_WINNING_STATE = 1000;
    public static final int SCORE_FOR_LOSING_STATE = -1000;

    private static final Random RANDOM = new Random();

    private static final Function1<IntStream, OptionalInt> MIN_FROM_STREAM = IntStream::min;
    private static final Function1<IntStream, OptionalInt> MAX_FROM_STREAM = IntStream::max;


    private static final Comparator<Node> HIGHEST_SCORE_COMPARATOR = (o1, o2) -> {
        int result = o1.score - o2.score;
        if (result == 0) {
            result = Double.compare(o1.avgScore, o2.avgScore);
        }
        if (result == 0) {
            result = Double.compare(o1.salt, o2.salt);
        }
        return result;
    };


    private final Team myTeam;

    private final int depthMax;

    private Node backup;

    private ExchangePoint<Connect4State, Integer> exchangePoint = new ExchangePoint<>();

    public int computeNextMove(@NonNull Connect4State state) throws InterruptedException {
        return exchangePoint.pushInputAndWaitOutput(state);
    }

    @Override
    protected @NonNull IterationCommand performOneIteration() throws Exception {
        final var state = exchangePoint.takeInput();

        int nextMove = -1;
        try {
            nextMove = evaluateNextMove(state);
        } catch (Throwable t) {
            if (ThrowableTool.isCausedByAnInterruption(t)) {
                throw t;
            } else {
                LOG.warn("Brain fart: pick a random move",t);
                nextMove = state.pickOneColumn(RANDOM);
            }
        }
        exchangePoint.putOutput(nextMove);

        return IterationCommand.CONTINUE;
    }

    private int evaluateNextMove(@NonNull Connect4State state) {
        if (state.isEmpty()) {
            this.backup = null;
            return Connect4Constants.INDEX_OF_MIDDLE_COLUMN;
        }

        final var root = Optional.ofNullable(this.backup)
                                 .map(n -> this.trimTree(n, state))
                                 .orElseGet(() -> new Node(state, myTeam,depthMax));
        final var bestMove = findBestMove(root);

        final int columnIndex = bestMove.map(Node::getState)
                                        .map(Connect4State::getColumnIndexOfLastMove)
                                        .orElseGet(() -> state.pickOneColumn(RANDOM));

        this.backup = bestMove.filter(Node::isMovePossible).orElse(null);

        return columnIndex;
    }


    private @NonNull Optional<Node> findBestMove(Node root) {
        if (root.isAWinningPosition() || root.isGridFull()) {
            throw new IllegalStateException("The game is already over");
        }
        root.depth = -1;
        final Stream<Node> streamOfChildren;
        if (root.children == null) {
            streamOfChildren = root.streamOfNewChildren();
        } else {
            streamOfChildren = root.streamChildren();
        }

        return streamOfChildren.parallel()
                               .map(n -> n.complete(0, depthMax))
                               .max(HIGHEST_SCORE_COMPARATOR);
    }


    private @NonNull Node trimTree(@NonNull Node root, @NonNull Connect4State state) {
        if (root.isAWinningPosition() || root.hasNoChildren()) {
            return root;
        }
        return root.streamChildren()
                   .filter(n -> n.isForState(state))
                   .findAny()
                   .orElse(null);
    }


    private static class Node {

        @Getter
        private final @NonNull Connect4State state;

        private final @NonNull Team myTeam;

        private final int depthMax;

        private int depth;

        private Node[] children;

        @Getter
        private int score;

        @Getter
        private double avgScore;

        @Getter
        private double salt;

        public Node(@NonNull Connect4State state, @NonNull Team myTeam, int depthMax) {
            this.state = state;
            this.myTeam = myTeam;
            this.depthMax = depthMax;
        }

        private @NonNull Node createForState(@NonNull Connect4State state) {
            return new Node(state,myTeam,depthMax);
        }

        public boolean isAWinningPosition() {
            return state.hasWinner();
        }

        public boolean isForState(@NonNull Connect4State state) {
            return Objects.equals(this.state.findLastMove(), state.findLastMove());
        }

        public boolean hasNoChildren() {
            return children == null;
        }

        public @NonNull Stream<Node> streamChildren() {
            return children == null ? Stream.empty() : Arrays.stream(children);
        }

        public void updateDepth(int current) {
            this.depth = current;
            streamChildren().forEach(n -> n.updateDepth(current + 1));
        }

        public Node complete(int depth, int depthMax) {
            checkNotInterrupted();

            this.depth = depth;
            if (depth < depthMax && isMovePossible()) {
                if (children == null) {
                    buildChildren();
                } else {
                    streamChildren().forEach(n -> n.complete(depth + 1, depthMax));
                }
            }
            this.salt = RANDOM.nextDouble();
            if (children == null || children.length == 0) {
                avgScore = score = scoreWithoutChildren();
            } else {
                avgScore = streamChildren().mapToDouble(n -> n.avgScore).average().orElse(0);
                final var evaluator = (state.getTeamOfLastMove() != myTeam) ? MAX_FROM_STREAM : MIN_FROM_STREAM;
                score = evaluator.f(streamChildren().mapToInt(Node::getScore).filter(s -> s!=0)).orElse(0);
            }
            return this;
        }

        private boolean isMovePossible() {
            return !state.isGridFull() && !state.hasWinner();
        }

        private void buildChildren() {
            checkNotInterrupted();
            this.children = streamOfNewChildren().map(n -> n.complete(depth + 1, depthMax))
                                                 .toArray(Node[]::new);
        }

        public @NonNull Stream<Node> streamOfNewChildren() {
            final var nextTeam = state.getTeamOfLastMove().getOtherType();
            return state.streamIndicesOfFreeColumns()
                        .mapToObj(i -> state.withPlayAt(nextTeam, i))
                        .map(this::createForState);
        }

        private int scoreWithoutChildren() {
            final var winningTeam = state.getWinningTeam().orElse(null);
            if (winningTeam == myTeam) {
                return SCORE_FOR_WINNING_STATE;
            } else if (winningTeam != null) {
                return SCORE_FOR_LOSING_STATE;
            } else {
                return evaluateStateScore(state);
            }
        }

        private int evaluateStateScore(Connect4State state) {
            return 0;
        }

        public boolean isGridFull() {
            return state.isGridFull();
        }
    }

}
