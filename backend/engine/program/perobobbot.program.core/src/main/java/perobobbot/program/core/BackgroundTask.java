package perobobbot.program.core;

import lombok.NonNull;
import perobobbot.common.lang.ThreadFactories;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public interface BackgroundTask {

    ScheduledExecutorService TASK_EXECUTOR = Executors.newScheduledThreadPool(2, ThreadFactories.daemon("background-tasks-%d"));

    interface Factory<S> {
        @NonNull
        BackgroundTask create(@NonNull S programState);
    }


    void start();

    void stop();


    BackgroundTask NOP = new BackgroundTask() {
        @Override
        public void start() {}

        @Override
        public void stop() {}
    };


}
