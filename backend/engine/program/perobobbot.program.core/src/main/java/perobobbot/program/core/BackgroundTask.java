package perobobbot.program.core;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.common.lang.ThreadFactories;
import perococco.perobobbot.program.core.MultiBackground;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * A program can have a background task.
 * If present the background task is launched when the program
 * started and interrupted when the program is stopped.
 *
 * This can be used to launch a task in a dedicated thread.
 */
public interface BackgroundTask {

    interface Factory<S> {
        @NonNull
        BackgroundTask create(@NonNull S programState);
    }

    /**
     * Start the background task
     */
    void start();

    void stop();

    BackgroundTask NOP = new BackgroundTask() {
        @Override
        public void start() {}

        @Override
        public void stop() {}
    };


    @NonNull
    static BackgroundTask multi(@NonNull BackgroundTask...tasks) {
        return new MultiBackground(tasks);
    }

    ScheduledExecutorService TASK_EXECUTOR = Executors.newScheduledThreadPool(2, ThreadFactories.daemon("background-tasks-%d"));

}
