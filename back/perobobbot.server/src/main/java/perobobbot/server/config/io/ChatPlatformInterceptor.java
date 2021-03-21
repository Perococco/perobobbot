package perobobbot.server.config.io;

import lombok.NonNull;
import perobobbot.chat.core.ChatConnection;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.chat.core.ProxyChatPlatform;
import perobobbot.data.service.BotService;
import perobobbot.lang.Bot;

import java.util.concurrent.CompletionStage;

public class ChatPlatformInterceptor {

    private final @NonNull ChatConnectionInterceptor interceptor;

    public ChatPlatformInterceptor(@NonNull BotService botService) {
        this.interceptor = new ChatConnectionInterceptor(botService);
    }

    public @NonNull ChatPlatform intercept(@NonNull ChatPlatform chatPlatform) {

        return new ProxyChatPlatform(chatPlatform) {
            @Override
            public @NonNull CompletionStage<? extends ChatConnection> connect(@NonNull Bot bot) {
                return super.connect(bot).thenApply(interceptor::intercept);
            }
        };
    }


}
