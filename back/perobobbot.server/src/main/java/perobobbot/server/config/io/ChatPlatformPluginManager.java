package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.core.MutableIO;
import perobobbot.data.com.event.ChatPlatformConnected;
import perobobbot.lang.MessageGateway;
import perobobbot.lang.Subscription;
import perobobbot.plugin.ChatPlatformPlugin;

import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public class ChatPlatformPluginManager {

    private final @NonNull MutableIO io;

    private final @NonNull MessageGateway messageGateway;

    private final @NonNull ChatPlatformInterceptor chatPlatformInterceptor;

    private final @NonNull Rejoiner rejoiner;

    public ChatPlatformPluginManager(@NonNull MutableIO mutableIO,
                                     @NonNull MessageGateway messageGateway,
                                     @NonNull Rejoiner rejoiner) {
        this.io = mutableIO;
        this.messageGateway = messageGateway;
        this.rejoiner = rejoiner;
        this.chatPlatformInterceptor = new ChatPlatformInterceptor(messageGateway);
    }

    public @NonNull Optional<Subscription> addChatPlatformPlugin(@NonNull ChatPlatformPlugin plugin) {
        final var chatPlatform = chatPlatformInterceptor.intercept(plugin.getChatPlatform());

        final var subscriptions = io.addPlatform(chatPlatform).map(
                s -> s.and(chatPlatform.addMessageListener(messageGateway::sendPlatformMessage))
        );

        messageGateway.sendEvent(new ChatPlatformConnected(chatPlatform.getPlatform()));

        rejoiner.rejoinChannels(chatPlatform.getPlatform());

        return subscriptions;

    }

}
