package perobobbot.program.core;

import lombok.NonNull;
import perobobbot.common.lang.ThreadFactories;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public interface Program {

    ScheduledExecutorService TASK_EXECUTOR = Executors.newScheduledThreadPool(4, ThreadFactories.daemon("background-tasks-%d"));

    /**
     * @return the name of the program
     */
    @NonNull
    String getName();

    /**
     * start the program.
     */
    void start();

    /**
     * stop the program.
     */
    void requestStop();

    /**
     * @return true if the program is running
     */
    boolean isRunning();

    @NonNull
    default ProgramInfo getInfo() {
        return new ProgramInfo(getName(),isRunning());
    }
}
