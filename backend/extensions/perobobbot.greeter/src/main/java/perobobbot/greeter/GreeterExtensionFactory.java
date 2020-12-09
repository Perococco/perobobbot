package perobobbot.greeter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.IO;
import perobobbot.extension.Extension;
import perobobbot.extension.ExtensionFactory;
import perobobbot.messaging.ChatController;

@RequiredArgsConstructor
public class GreeterExtensionFactory implements ExtensionFactory {

    public static final String NAME = "greeter";

    private final @NonNull IO io;
    private final @NonNull ChatController chatController;


    @Override
    public @NonNull Extension create(@NonNull String userId) {
        return new GreeterExtension(userId, io, chatController);
    }

    @Override
    public @NonNull String getExtensionName() {
        return NAME;
    }
}
