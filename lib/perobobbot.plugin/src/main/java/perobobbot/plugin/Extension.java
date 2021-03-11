package perobobbot.plugin;

import lombok.NonNull;
import perobobbot.lang.ThreadFactories;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public interface Extension {

    /**
     * @return the name of this extension
     */
    @NonNull String getName();

    /**
     * enable this extension
     */
    void enable();

    /**
     * disable this extension
     */
    void disable();

    /**
     * @return true if this extension is enabled, false otherwise
     */
    boolean isEnabled();

}
