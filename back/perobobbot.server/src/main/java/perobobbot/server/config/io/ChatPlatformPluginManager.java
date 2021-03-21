package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.core.Chat;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.chat.core.MutableIO;
import perobobbot.data.com.JoinedChannel;
import perobobbot.data.service.BotService;
import perobobbot.data.service.EventService;
import perobobbot.lang.Platform;
import perobobbot.lang.Subscription;
import perobobbot.plugin.ChatPlatformPlugin;
import perobobbot.server.component.MessageGateway;

import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public class ChatPlatformPluginManager {

    private final @NonNull MutableIO io;

    private final @NonNull MessageGateway messageGateway;

    private final @NonNull BotService botService;

    private final @NonNull ChatPlatformInterceptor chatPlatformInterceptor;

    public ChatPlatformPluginManager(@NonNull MutableIO mutableIO,
                                     @NonNull MessageGateway messageGateway,
                                     @NonNull BotService botService) {
        this.io = mutableIO;
        this.messageGateway = messageGateway;
        this.botService = botService;
        this.chatPlatformInterceptor = new ChatPlatformInterceptor(botService);
    }

    public @NonNull Optional<Subscription> addChatPlatformPlugin(@NonNull ChatPlatformPlugin plugin) {
        final var chatPlatform = chatPlatformInterceptor.intercept(plugin.getChatPlatform());

        final var subscriptions = io.addPlatform(chatPlatform)
                                    .map(s -> s.and(
                                            chatPlatform.addMessageListener(messageGateway::sendPlatformMessage)));

        Rejoiner.rejoin(botService, chatPlatform);

        return subscriptions;

    }

}
