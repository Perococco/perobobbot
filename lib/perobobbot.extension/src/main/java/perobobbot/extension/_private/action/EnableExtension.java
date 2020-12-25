package perobobbot.extension._private.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.extension.ExtensionManager;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.Executor;

@RequiredArgsConstructor
public class EnableExtension implements Executor<ExecutionContext> {

    private final @NonNull ExtensionManager extensionManager;

    @Override
    public void execute(@NonNull ExecutionContext context) {
        extensionManager.enableExtension(context.getParameters());
    }

}
