package perobobbot.server.config.io;

import lombok.NonNull;
import perobobbot.chat.core.ChatConnection;
import perobobbot.chat.core.MessageChannelIO;
import perobobbot.chat.core.ProxChatConnection;
import perobobbot.data.service.BotService;
import perobobbot.lang.Platform;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

public class ChatConnectionInterceptor extends ProxChatConnection {

    private final @NonNull BotService botService;

    public ChatConnectionInterceptor(@NonNull ChatConnection delegate, @NonNull BotService botService) {
        super(delegate);
        this.botService = botService;
    }

    @Override
    public @NonNull CompletionStage<? extends MessageChannelIO> join(@NonNull String channelName) {
        System.out.println("Join "+getPlatform()+"/"+channelName);
        return super.join(channelName).whenComplete((r,t) -> {
            if (r != null) {
                final var botId = this.getChatConnectionInfo().getBotId();
                final var platform = this.getPlatform();
                saveConnection(botId, platform, channelName);
            }
        });
    }

    private void saveConnection(@NonNull UUID botId, @NonNull Platform platform, @NonNull String channelName) {
        botService.saveChannelConnection(botId,platform,channelName);
    }
}
