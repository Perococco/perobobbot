package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.MutableIO;
import perobobbot.lang.Subscription;
import perobobbot.plugin.ChatPlatformPlugin;
import perobobbot.server.component.MessageGateway;

import java.util.Optional;

@RequiredArgsConstructor
public class ChatPlatformPluginManager {

    private final @NonNull MutableIO io;

    private final @NonNull MessageGateway messageGateway;

    public @NonNull Optional<Subscription> addChatPlatformPlugin(@NonNull ChatPlatformPlugin plugin) {
        final var chatPlatform = plugin.getChatPlatform();

        return io.addPlatform(chatPlatform)
          .map(s -> s.and(chatPlatform.addMessageListener(messageGateway::sendPlatformMessage)));
    }

}
