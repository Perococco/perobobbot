package perobobbot.extension;

import lombok.Synchronized;

public abstract class ExtensionBase implements Extension {

    private volatile boolean enabled = false;

    @Override
    @Synchronized
    public void enable() {
        if (enabled) {
            return;
        }
        enabled = true;
        onEnabled();
    }

    @Override
    @Synchronized
    public void disable() {
        if (!enabled) {
            return;
        }
        enabled = false;
        onDisabled();
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    protected void onEnabled() {};
    protected void onDisabled() {};

}
