package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.core.ChatConnection;
import perobobbot.chat.core.MessageChannelIO;
import perobobbot.chat.core.ProxyChatConnection;
import perobobbot.data.com.event.ChatChannelJoined;
import perobobbot.server.component.MessageGateway;

import java.util.concurrent.CompletionStage;

@Log4j2
public class ChatConnectionInterceptor {

    private final @NonNull MessageGateway messageGateway;

    public ChatConnectionInterceptor(@NonNull MessageGateway messageGateway) {
        this.messageGateway = messageGateway;
    }

    public @NonNull ChatConnection intercept(@NonNull ChatConnection chatConnection) {
        return new ProxyChatConnection(chatConnection) {
            @Override
            public @NonNull CompletionStage<? extends MessageChannelIO> join(@NonNull String channelName) {
                return super.join(channelName).whenComplete((messageChannelIO, error) -> {
                    if (messageChannelIO != null) {
                        final var connectionInfo = getChatConnectionInfo();
                        LOG.info("{} join {}/{}", connectionInfo.getBotName(), connectionInfo.getPlatform(), channelName);
                        final var joinEvent = new ChatChannelJoined(connectionInfo.getPlatform(), connectionInfo.getBotId(), channelName);
                        messageGateway.sendEvent(joinEvent);
                    }
                });
            }
        };
    }

}
