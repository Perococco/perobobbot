package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.core.ChatConnection;
import perobobbot.chat.core.MessageChannelIO;
import perobobbot.chat.core.ProxChatConnection;
import perobobbot.data.service.BotService;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.lang.Platform;
import perobobbot.lang.ThrowableTool;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

@Log4j2
public class ChatConnectionInterceptor {

    private final @NonNull BotService botService;

    public ChatConnectionInterceptor(@NonNull BotService botService) {
        this.botService = botService;
    }

    public @NonNull ChatConnection intercept(@NonNull ChatConnection chatConnection) {
        return new ProxChatConnection(chatConnection) {
            @Override
            public @NonNull CompletionStage<? extends MessageChannelIO> join(@NonNull String channelName) {
                return super.join(channelName).whenComplete((r, t) -> {
                    if (r != null) {
                        final var connectionInfo = getChatConnectionInfo();
                        LOG.info("{} join {}/{}", connectionInfo.getBotName(), connectionInfo.getPlatform(), channelName);
                        final var saver = createSaver(connectionInfo,channelName);
                        new Thread(saver::save).start();
                    }
                });
            }
        };
    }

    public @NonNull ConnectionSaver createSaver(@NonNull ChatConnectionInfo connectionInfo, @NonNull String channelName) {
        return new ConnectionSaver(botService,connectionInfo.getBotId(), connectionInfo.getPlatform(), channelName);
    }
    @RequiredArgsConstructor
    private static class ConnectionSaver {

        private final @NonNull BotService botService;
        private final @NonNull UUID botId;
        private final @NonNull Platform platform;
        private final @NonNull String channelName;

        public void save() {
            try {
                botService.saveChannelConnection(botId, platform, channelName);
            } catch (Exception e) {
                ThrowableTool.interruptThreadIfCausedByInterruption(e);
                LOG.warn("Could not save channel connection information : {}",e.getMessage());
            }
        }
    }

}
