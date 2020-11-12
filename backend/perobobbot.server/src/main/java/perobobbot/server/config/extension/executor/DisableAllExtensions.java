package perobobbot.server.config.extension.executor;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.extension.ExtensionManager;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.Executor;

@RequiredArgsConstructor
public class DisableAllExtensions implements Executor<ExecutionContext> {

    private final @NonNull ExtensionManager extensionManager;

    @Override
    public void execute(@NonNull ExecutionContext context) {
        extensionManager.disableAll();
    }
}
