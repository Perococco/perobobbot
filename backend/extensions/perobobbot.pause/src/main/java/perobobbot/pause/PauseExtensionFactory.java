package perobobbot.pause;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.PolicyManager;
import perobobbot.command.CommandRegistry;
import perobobbot.extension.Extension;
import perobobbot.extension.ExtensionFactory;
import perobobbot.overlay.api.Overlay;

import static perobobbot.lang.Todo.TODO;

@RequiredArgsConstructor
public class PauseExtensionFactory implements ExtensionFactory {

    public static final String NAME = "pause";

    private final @NonNull Overlay overlay;
    private final @NonNull CommandRegistry commandRegistry;
    private final @NonNull PolicyManager policyManager;

    @Override
    public @NonNull Extension create(@NonNull String userId) {
        return TODO();
    }

    @Override
    public @NonNull String getExtensionName() {
        return NAME;
    }
}
