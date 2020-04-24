package bot.twitch.chat.event;

import bot.twitch.chat.message.from.MessageFromTwitch;
import bot.twitch.chat.message.to.MessageToTwitch;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Getter
public class PostedMessage implements TwitchChatEvent {

    @NonNull
    private final Instant dispatchingTime;

    @NonNull
    @Getter
    private final MessageToTwitch postedMessage;

}
