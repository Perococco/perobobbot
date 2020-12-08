package perobobbot.extension._private.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.extension.ExtensionInfo;
import perobobbot.extension.ExtensionManager;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.Executor;
import perobobbot.chat.core.IO;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ListExtensions implements Executor<ExecutionContext> {

    @NonNull
    private final ExtensionManager extensionManager;

    @NonNull
    private final IO io;

    @Override
    public void execute(@NonNull ExecutionContext context) {
        final String message = extensionManager.getExtensionInfo()
                      .stream()
                      .sorted()
                      .map(this::format)
                      .collect(Collectors.joining(", "));

        io.send(extensionManager.getUserId(), context.getChannelInfo(), message);
    }

    @NonNull
    private String format(@NonNull ExtensionInfo extensionInfo) {
        return extensionInfo.getExtensionName()+(extensionInfo.isEnabled()?"*":"");
    }
}
