package perobobbot.connect4.game;

import lombok.NonNull;
import lombok.Synchronized;
import perobobbot.connect4.GridIsFull;
import perobobbot.lang.Looper;
import perobobbot.lang.ThrowableTool;
import perobobbot.lang.fp.Value2;

import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class AbstractPlayer implements Player {

    private Runner runner = null;

    @Override
    @Synchronized
    public @NonNull CompletionStage<Integer> getPlayerMove(@NonNull Connect4State currentState) {
        if (runner == null) {
            runner = new Runner();
            runner.start();
        }
        final CompletableFuture<Integer> result = new CompletableFuture<>();
        runner.offer(currentState, result);
        return result;
    }


    @Override
    @Synchronized
    public void dispose() {
        if (runner != null) {
            runner.requestStop();
            runner = null;
        }
    }

    private class Runner extends Looper {

        private final BlockingDeque<Value2<Connect4State, CompletableFuture<Integer>>> queue = new LinkedBlockingDeque<>();

        public void offer(@NonNull Connect4State currentState, @NonNull CompletableFuture<Integer> result) {
            queue.addLast(Value2.of(currentState, result));
        }

        @Override
        protected @NonNull IterationCommand performOneIteration() throws Exception {
            final var v = queue.take();
            final var state = v.getA();
            final var result = v.getB();

            try {
                if (state.isFull()) {
                    throw new GridIsFull(getTeam());
                } else if (state.onlyOneColumnLeft()) {
                    result.complete(state.pickOneColumn());
                } else {
                    result.complete(getNextMove(state));
                }
            } catch (Throwable t) {
                ThrowableTool.interruptThreadIfCausedByInterruption(t);
                result.completeExceptionally(t);
            }

            return IterationCommand.CONTINUE;
        }

        @Override
        protected void afterLooping() {
            final var list = new ArrayList<Value2<Connect4State, CompletableFuture<Integer>>>(queue.size() + 10);
            queue.drainTo(list);
            list.forEach(v -> v.getB().completeExceptionally(new InterruptedException("Player interrupted")));
        }
    }

    protected abstract int getNextMove(@NonNull Connect4State state) throws Throwable;
}
