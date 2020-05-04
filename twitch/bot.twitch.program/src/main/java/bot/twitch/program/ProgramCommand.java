package bot.twitch.program;

import bot.twitch.chat.Channel;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.event.ReceivedMessage;
import bot.twitch.chat.message.from.PrivMsgFromTwitch;
import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@RequiredArgsConstructor
@Builder
public class ProgramCommand {

    /**
     * The state of the Twitch chat when the message was received
     */
    @NonNull
    @Getter
    private final TwitchChatState state;

    /**
     * The time when the message was received
     */
    @NonNull
    @Getter
    private final Instant receptionTime;

    /**
     * The received message
     */
    @NonNull
    private final PrivMsgFromTwitch message;

    @NonNull
    @Getter
    private final String commandName;

    @NonNull
    @Getter
    private final ImmutableList<String> parameters;


    @NonNull
    public String getFirstParameter() {
        return parameters.get(0);
    }

    @NonNull
    public Channel channel() {
        return message.channel();
    }

    public String user() {
        return message.user();
    }
}
