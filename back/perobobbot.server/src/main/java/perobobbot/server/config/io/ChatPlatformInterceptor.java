package perobobbot.server.config.io;

import lombok.NonNull;
import perobobbot.chat.core.ChatConnection;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.chat.core.ProxyChatPlatform;
import perobobbot.data.service.BotService;
import perobobbot.lang.Bot;

import java.util.concurrent.CompletionStage;

public class ChatPlatformInterceptor extends ProxyChatPlatform {

    private final @NonNull BotService botService;

    public ChatPlatformInterceptor(@NonNull ChatPlatform delegate, @NonNull BotService botService) {
        super(delegate);
        this.botService = botService;
    }

    @Override
    public @NonNull CompletionStage<? extends ChatConnection> connect(@NonNull Bot bot) {
        return super.connect(bot).thenApply(c -> new ChatConnectionInterceptor(c,botService));
    }
}
