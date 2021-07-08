package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.core.ChatConnection;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.chat.core.ProxyChatPlatform;
import perobobbot.lang.ChatConnectionInfo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Log4j2
public class RefreshChatPlatform extends ProxyChatPlatform {

    public RefreshChatPlatform(@NonNull ChatPlatform delegate) {
        super(delegate);
    }

    @Override
    public @NonNull CompletionStage<ChatConnection> connect(@NonNull ChatConnectionInfo chatConnectionInfo) {
        return super.connect(chatConnectionInfo)
                    .exceptionallyComposeAsync(e -> tryReconnect(e, chatConnectionInfo));

    }

    private CompletionStage<ChatConnection> tryReconnect(Throwable error, @NonNull ChatConnectionInfo chatConnectionInfo) {
        System.out.println("THREAD INTERRUPTED : "+Thread.currentThread().isInterrupted());
        final var refreshedChatConnectionInfo = chatConnectionInfo.refresh().orElse(null);
        if (refreshedChatConnectionInfo == null) {
            LOG.warn("Chat connection failed and token cannot be refreshed");
            return CompletableFuture.failedFuture(error);
        }
        LOG.warn("Retry chat connection with refreshed token");

        return refreshedChatConnectionInfo.refresh()
                                 .map(super::connect)
                                 .orElseGet(() -> CompletableFuture.failedFuture(error));
    }
}
