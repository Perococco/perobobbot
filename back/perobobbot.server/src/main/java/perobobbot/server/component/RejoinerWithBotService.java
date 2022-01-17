package perobobbot.server.component;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.chat.core.IO;
import perobobbot.data.service.BotService;
import perobobbot.data.service.EventService;
import perobobbot.data.service.OAuthService;
import perobobbot.lang.JoinedChannel;
import perobobbot.lang.Platform;
import perobobbot.lang.PlatformBot;
import perobobbot.server.config.io.ChatConnectionHelper;
import perobobbot.server.config.io.Rejoiner;

@Log4j2
@RequiredArgsConstructor
@Component
public class RejoinerWithBotService implements Rejoiner {

    private final @NonNull IO io;

    private final @EventService
    @NonNull BotService botService;

    private final @EventService
    @NonNull OAuthService oAuthService;

    @Override
    public void rejoinChannels(@NonNull Platform platform) {
        io.findPlatform(platform).map(Execution::new).ifPresent(Execution::run);
    }

    private class Execution {

        private final @NonNull ChatPlatform chatPlatform;
        private final @NonNull ChatConnectionHelper chatConnectionHelper;

        private ImmutableList<JoinedChannel> joinedChannels;

        public Execution(@NonNull ChatPlatform chatPlatform) {
            this.chatPlatform = chatPlatform;
            this.chatConnectionHelper = new ChatConnectionHelper(oAuthService);
        }

        private void run() {
            this.retrieveJoinedChannels();
            assert joinedChannels != null;
            joinedChannels.forEach(this::rejoin);
        }

        private void retrieveJoinedChannels() {
            this.joinedChannels = botService.findJoinedChannels(chatPlatform.getPlatform());
        }

        private void rejoin(@NonNull JoinedChannel joinedChannel) {
            final var channelId = joinedChannel.getChannelId();
            final var platformBot = joinedChannel.getPlatformBot();
            final var connectionInfo = chatConnectionHelper.createRefreshable(joinedChannel).orElse(null);

            if (connectionInfo == null) {
                warnOnRejoinFailure("No credential to join", platformBot, channelId);
                return;
            }

            if (!connectionInfo.getPlatform().equals(chatPlatform.getPlatform())) {
                warnOnRejoinFailure("Connection information are not for this platform : this=" + chatPlatform.getPlatform() + " that=" + connectionInfo.getPlatform(), platformBot, channelId);
                return;
            }


            chatPlatform.connect(connectionInfo)
                        .thenCompose(connection -> connection.join(channelId))
                        .whenComplete((r, error) -> {
                            if (error != null) {
                                warnOnRejoinFailure(error, platformBot, channelId);
                            }
                        });

        }

        private void warnOnRejoinFailure(@NonNull Throwable error, @NonNull PlatformBot bot, @NonNull String channelName) {
            warnOnRejoinFailure(error.getMessage(), bot, channelName);
            LOG.debug(error);

        }

        private void warnOnRejoinFailure(@NonNull String message, @NonNull PlatformBot bot, @NonNull String channelName) {
            LOG.warn("Fail to connect to channel {}/{} with bot {} : {}",
                    chatPlatform.getPlatform(),
                    channelName,
                    bot.getBotName(),
                    message);
        }

    }

}
