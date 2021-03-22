package perobobbot.server.component;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.chat.core.IO;
import perobobbot.data.com.JoinedChannel;
import perobobbot.data.service.BotService;
import perobobbot.data.service.EventService;
import perobobbot.lang.Bot;
import perobobbot.lang.Platform;
import perobobbot.server.config.io.Rejoiner;

@Log4j2
@RequiredArgsConstructor
@Component
public class RejoinerWithBotService implements Rejoiner {

    private final @NonNull IO io;

    private final @EventService @NonNull BotService botService;

    @Override
    public void rejoinChannels(@NonNull Platform platform) {
        io.findPlatform(platform).map(Execution::new).ifPresent(Execution::run);
    }

    @RequiredArgsConstructor
    private class Execution {

        private final @NonNull ChatPlatform chatPlatform;

        private ImmutableList<JoinedChannel> joinedChannels;

        private void run() {
            this.retrieveJoinedChannels();
            assert joinedChannels != null;
            joinedChannels.forEach(this::rejoin);
        }

        private void retrieveJoinedChannels() {
            this.joinedChannels = botService.findConnections(chatPlatform.getPlatform());
        }

        private void rejoin(@NonNull JoinedChannel joinedChannel) {
            final var channelName = joinedChannel.getChannelName();
            final var bot = joinedChannel.getBot();

            chatPlatform.connect(bot)
                        .thenCompose(c -> c.join(channelName))
                        .whenComplete((r, t) -> {
                            if (t != null) {
                                warnOnRejoinFailure(t, bot, channelName);
                            }
                        });

        }

        private void warnOnRejoinFailure(@NonNull Throwable error, @NonNull Bot bot, @NonNull String channelName) {
            LOG.warn("Fail to connect to channel {}/{} with bot {} : {}", chatPlatform.getPlatform(), channelName,
                     bot.getName(),
                     error.getMessage());
            LOG.debug(error);

        }

    }

}
