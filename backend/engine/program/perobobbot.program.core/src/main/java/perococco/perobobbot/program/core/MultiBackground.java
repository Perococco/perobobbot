package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.ThrowableTool;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.program.core.BackgroundTask;

@RequiredArgsConstructor
public class MultiBackground implements BackgroundTask {

    @NonNull
    private final ImmutableList<BackgroundTask> tasks;

    public MultiBackground(@NonNull BackgroundTask[] tasks) {
        this(ImmutableList.copyOf(tasks));
    }

    @Override
    public void start() {
        tasks.forEach(b -> safeExecute(b, BackgroundTask::start));
    }

    @Override
    public void stop() {
        tasks.forEach(b -> safeExecute(b, BackgroundTask::stop));
    }

    private void safeExecute(@NonNull BackgroundTask task, @NonNull Consumer1<? super BackgroundTask> action) {
        try {
            action.accept(task);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
        }
    }
}
