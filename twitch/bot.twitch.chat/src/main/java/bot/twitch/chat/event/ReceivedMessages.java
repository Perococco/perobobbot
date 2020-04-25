package bot.twitch.chat.event;

import bot.twitch.chat.message.from.MessageFromTwitch;
import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
public class ReceivedMessages implements TwitchChatEvent {

    @NonNull
    @Getter
    private final Instant receptionTime;

    @NonNull
    @Getter
    private final ImmutableList<MessageFromTwitch> messages;

    @Override
    public String toString() {
        return "ReceivedMessages{" +
               "messages=" + messages +
               '}';
    }
}
