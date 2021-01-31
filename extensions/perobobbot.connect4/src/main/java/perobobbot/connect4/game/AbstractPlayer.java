package perobobbot.connect4.game;

import lombok.*;
import perobobbot.connect4.GridIsFull;
import perobobbot.lang.Looper;
import perobobbot.lang.ThrowableTool;

import java.util.ArrayList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class AbstractPlayer implements Player {

    private PlayerRunner runner = null;

    @Override
    @Synchronized
    public @NonNull CompletionStage<Integer> getPlayerMove(@NonNull Connect4State currentState) {
        this.startIfNotRunning();
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

    private void startIfNotRunning() {
        if (runner == null || !runner.isRunning()) {
            runner = new PlayerRunner();
            runner.start();
        }
    }

    private class PlayerRunner extends Looper {

        private final BlockingDeque<MoveData> queue = new LinkedBlockingDeque<>();

        public void offer(@NonNull Connect4State currentState, @NonNull CompletableFuture<Integer> result) {
            queue.addLast(new MoveData(currentState, result));
        }

        @Override
        protected @NonNull IterationCommand performOneIteration() throws Exception {
            final var data = queue.take();
            final var state = data.getState();

            try {
                if (state.isFull()) {
                    throw new GridIsFull(getTeam());
                } else if (state.onlyOneFreeColumnLeft()) {
                    data.complete(state.pickOneColumn());
                } else {
                    data.complete(getNextMove(state));
                }
            } catch (Throwable t) {
                ThrowableTool.interruptThreadIfCausedByInterruption(t);
                data.completeExceptionally(t);
            }

            return IterationCommand.CONTINUE;
        }

        @Override
        protected void afterLooping() {
            final var list = new ArrayList<MoveData>(queue.size() + 10);
            queue.drainTo(list);
            list.forEach(v -> v.completeExceptionally(new InterruptedException("Player interrupted")));
        }
    }

    protected abstract int getNextMove(@NonNull Connect4State state) throws Throwable;


    @RequiredArgsConstructor
    private static class MoveData {

        @Getter
        private final @NonNull Connect4State state;
        private final @NonNull CompletableFuture<Integer> future;

        public void completeExceptionally(@NonNull Throwable throwable) {
            this.future.completeExceptionally(throwable);
        }

        public void complete(int nextMove) {
            this.future.complete(nextMove);
        }
    }
}
