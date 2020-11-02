package perobobbot.program.core;

import lombok.Synchronized;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ProgramBase implements Program {

    private final AtomicBoolean enabled = new AtomicBoolean(false);

    @Override
    @Synchronized
    public final void enable() {
        if (enabled.compareAndSet(false, true)) {
            onEnabled();
        }
    }

    @Override
    @Synchronized
    public final void disable() {
        if(enabled.compareAndSet(true,false)) {
            onDisabled();
        }
    }

    protected void onEnabled() {};

    protected void onDisabled() {};

    @Override
    public boolean isEnabled() {
        return enabled.get();
    }
}
