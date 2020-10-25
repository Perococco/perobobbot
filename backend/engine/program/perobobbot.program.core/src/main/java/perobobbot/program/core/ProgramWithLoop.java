package perobobbot.program.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.Looper;

@RequiredArgsConstructor
public abstract class ProgramWithLoop implements Program {

    @Getter
    private final @NonNull String name;

    private final Looper looper = Looper.basic(this::performOneIteration);

    @Override
    public void start() {
        looper.start();
    }

    @Override
    public void requestStop() {
        looper.requestStop();
    }

    @Override
    public boolean isRunning() {
        return looper.isRunning();
    }

    protected abstract Looper.IterationCommand performOneIteration();


}
