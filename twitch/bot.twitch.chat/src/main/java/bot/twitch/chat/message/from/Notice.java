package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.Channel;
import bot.twitch.chat.message.IRCCommand;
import bot.twitch.chat.message.TagKey;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
@Builder
@Getter
public class Notice implements KnownMessageFromTwitch {

    @NonNull
    private final IRCParsing ircParsing;

    @NonNull
    private final Channel channel;

    @NonNull
    private final String msgId;

    @NonNull
    private final String message;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.NOTICE;
    }


    public static @NonNull Notice build(@NonNull AnswerBuilderHelper helper) {
        return Notice.builder()
                     .ircParsing(helper.ircParsing())
                     .channel(helper.channelFormParameterAt(0))
                     .message(helper.lastParameter())
                     .msgId(helper.tagValue(TagKey.MSG_ID))
                     .build();
    }
}
