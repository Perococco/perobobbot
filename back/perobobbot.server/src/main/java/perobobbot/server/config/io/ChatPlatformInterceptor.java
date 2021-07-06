package perobobbot.server.config.io;

import lombok.NonNull;
import perobobbot.chat.core.ChatConnection;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.chat.core.ProxyChatPlatform;
import perobobbot.lang.Bot;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.lang.MessageGateway;

import java.util.concurrent.CompletionStage;

public class ChatPlatformInterceptor {

    private final @NonNull ChatConnectionInterceptor interceptor;

    public ChatPlatformInterceptor(@NonNull MessageGateway messageGateway) {
        this.interceptor = new ChatConnectionInterceptor(messageGateway);
    }

    public @NonNull ChatPlatform intercept(@NonNull ChatPlatform chatPlatform) {

        return new ProxyChatPlatform(chatPlatform) {
            @Override
            public @NonNull CompletionStage<? extends ChatConnection> connect(@NonNull ChatConnectionInfo chatConnectionInfo) {
                return super.connect(chatConnectionInfo).thenApply(interceptor::intercept);
            }
        };
    }


}
