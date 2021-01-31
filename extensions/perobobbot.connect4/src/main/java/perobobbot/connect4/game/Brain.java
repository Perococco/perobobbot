package perobobbot.connect4.game;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.Connect4Constants;
import perobobbot.connect4.Team;
import perobobbot.lang.Looper;
import perobobbot.lang.fp.Either;

import java.util.*;
import java.util.concurrent.SynchronousQueue;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class Brain extends Looper {

    public static final int SCORE_FOR_WINNING_STATE = 1000;
    public static final int SCORE_FOR_LOSING_STATE = -1000;


    private final Team myTeam;

    private final int depthMax;

    private Node root;

    private SynchronousQueue<Either<Connect4State, Integer>> exchangeQueue = new SynchronousQueue<>();

    public int computeNextMove(@NonNull Connect4State state) throws InterruptedException {
        this.exchangeQueue.put(Either.left(state));
        return takeMoveIndex();
    }

    @Override
    protected @NonNull IterationCommand performOneIteration() throws Exception {
        final var state = takeConnectedState();

        if (state.isEmpty()) {
            exchangeQueue.put(Either.right(Connect4Constants.INDEX_OF_MIDDLE_COLUMN));
            return IterationCommand.CONTINUE;
        }

        this.root = Optional.ofNullable(this.root)
                            .map(r -> this.trimTree(r, state))
                            .map(this::completeTree)
                            .orElseGet(() -> buildTree(state));

        this.evaluateScores();
        final var bestMove = this.evaluateBestMove();

        final var columnIndex = bestMove.getColumnIndexOfLastMove();

        this.exchangeQueue.put(Either.right(columnIndex));

        trimTree(root, bestMove);

        return IterationCommand.CONTINUE;
    }

    private @NonNull Node buildTree(Connect4State currentState) {
        return buildNode(currentState, 0);
    }

    private @NonNull Node trimTree(@NonNull Node root, @NonNull Connect4State state) {
        if (root.isAWinningPosition() || root.hasNoChildren()) {
            return root;
        }
        final var node = root.streamChildren()
                             .filter(n -> n.isForState(state))
                             .findAny()
                             .orElse(null);
        if (node != null) {
            node.updateDepth(0);
        }
        return node;
    }

    private Node completeTree(@NonNull Node node) {
        return node.complete(depthMax);
    }

    private void evaluateScores() {
        this.root.evaluateScores();
    }

    private @NonNull Connect4State evaluateBestMove() {
        return root.streamChildren()
                   .max(Comparator.comparingDouble(Node::getScore)
                                  .thenComparingDouble(Node::getAvgScore)
                                    .thenComparingDouble(Node::getSalt)
                   )
                   .map(Node::getState)
                   .orElseThrow(() -> new RuntimeException("BUG in children building. Some were expected"));
    }


    private Node buildNode(Connect4State state, int currentDepth) {
        final var node = new Node(state, currentDepth);
        if (state.hasWinner() || currentDepth >= depthMax) {
            return node;
        }
        node.buildChildren();
        return node;
    }

    private @NonNull Team getTeamForState(@NonNull Connect4State state) {
        return state.getTeamOfLastMove();
    }


    private @NonNull Connect4State takeConnectedState() throws InterruptedException {
        return exchangeQueue.take().left().orElseThrow(() -> new RuntimeException("BUG: a connect4state was expected"));
    }

    private @NonNull int takeMoveIndex() throws InterruptedException {
        return exchangeQueue.take().right().orElseThrow(() -> new RuntimeException("BUG: a connect4state was expected"));
    }

    private final Random random = new Random();

    private class Node {

        @Getter
        private final @NonNull Connect4State state;

        private int depth;

        private Node[] children;

        @Getter
        private int score;

        @Getter
        private double avgScore;

        @Getter
        private double salt;

        public Node(@NonNull Connect4State state, int depth) {
            this.state = state;
            this.depth = depth;
        }

        public boolean isAWinningPosition() {
            return state.hasWinner();
        }

        public boolean isForState(@NonNull Connect4State state) {
            return Objects.equals(this.state.findLastMove(),state.findLastMove());
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

        public Node complete(int depthMax) {
            if (depth >= depthMax) {
                return this;
            }
            if (children == null) {
                this.buildChildren();
            } else {
                streamChildren().forEach(n -> n.complete(depthMax));
            }
            return this;
        }

        public void buildChildren() {
            checkNotInterrupted();
            final var nextTeam = getTeamForState(state).getOtherType();
            this.children = state.streamIndicesOfFreeColumns()
                                 .mapToObj(i -> state.withPlayAt(nextTeam, i))
                                 .map(s -> buildNode(s, depth + 1))
                                 .toArray(Node[]::new);
        }

        public void evaluateScores() {
            checkNotInterrupted();
            this.salt = random.nextDouble();
            final var winningTeam = state.getWinningTeam().orElse(null);
            if (winningTeam == myTeam) {
                avgScore = score = SCORE_FOR_WINNING_STATE;
            } else if (winningTeam != null) {
                avgScore = score = SCORE_FOR_LOSING_STATE;
            } else if (children == null) {
                avgScore = score = evaluateStateScore(state);
            } else {
                streamChildren().forEach(Node::evaluateScores);

                avgScore = streamChildren().mapToDouble(n -> n.avgScore).average().orElse(0);

                final var amIPlaying = getTeamForState(state) == myTeam;
                if (amIPlaying) {
                    score = streamChildren().mapToInt(Node::getScore)
                                            .filter(v->v!=0)
                                            .min()
                                            .orElse(0);
                } else {
                    score = streamChildren().mapToInt(Node::getScore)
                                            .filter(v->v!=0)
                                            .max()
                                            .orElse(0);
                }
            }
        }

        private int evaluateStateScore(Connect4State state) {
            return 0;
        }
    }

}
