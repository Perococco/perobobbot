package perobobbot.server.config.io;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.data.com.JoinedChannel;
import perobobbot.data.service.BotService;
import perobobbot.lang.Bot;

@Log4j2
@RequiredArgsConstructor
public class Rejoiner {

    public static void rejoin(@NonNull BotService botService, @NonNull ChatPlatform chatPlatform) {
        new Rejoiner(botService,chatPlatform).rejoin();
    }

    private final @NonNull BotService botService;

    private final @NonNull ChatPlatform chatPlatform;

    private ImmutableList<JoinedChannel> joinedChannels;

    private void rejoin() {
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
        LOG.warn("Fail to connect to channel {}/{} with bot {} : {}", chatPlatform.getPlatform(), channelName, bot.getName(),
                 error.getMessage());
        LOG.debug(error);

    }



}
