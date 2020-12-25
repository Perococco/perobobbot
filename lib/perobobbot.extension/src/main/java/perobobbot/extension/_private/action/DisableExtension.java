package perobobbot.extension._private.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.extension.ExtensionManager;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.Executor;

@RequiredArgsConstructor
public class DisableExtension implements Executor<ExecutionContext> {

    private final @NonNull ExtensionManager extensionManager;

    @Override
    public void execute(@NonNull ExecutionContext context) {
        extensionManager.disableExtension(context.getParameters());
    }

}
