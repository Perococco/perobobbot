package perobobbot.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleCommandBundleLifeCycle implements CommandBundleLifeCycle {

    private final @NonNull CommandRegistry commandRegistry;
    private final @NonNull CommandBundle commandBundle;

    @Override
    public void attachCommandBundle() {
        commandBundle.attachTo(commandRegistry);
    }

    @Override
    public void detachCommandBundle() {
        commandBundle.detach();
    }
}
