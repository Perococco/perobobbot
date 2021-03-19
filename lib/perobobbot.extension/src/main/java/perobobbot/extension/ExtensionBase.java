package perobobbot.extension;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import perobobbot.plugin.Extension;

@RequiredArgsConstructor
public abstract class ExtensionBase implements Extension {

    private volatile boolean enabled = false;

    @Getter
    private final @NonNull String name;

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
