package perobobbot.server.config.extension.executor;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.Executor;
import perobobbot.extension.ExtensionManager;

@RequiredArgsConstructor
public class DisableExtension implements Executor<ExecutionContext> {

    private final @NonNull ExtensionManager extensionManager;

    @Override
    public void execute(@NonNull ExecutionContext context) {
        extensionManager.disableExtension(context.getParameters());
    }

}
