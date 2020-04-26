package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.Channel;
import bot.twitch.chat.ChannelSpecific;
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
public class Notice extends KnownMessageFromTwitch implements ChannelSpecific {

    @NonNull
    private final IRCParsing ircParsing;

    @NonNull
    private final Channel channel;

    @NonNull
    private final NoticeId msgId;

    @NonNull
    private final String message;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.NOTICE;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }


    public static @NonNull Notice build(@NonNull AnswerBuilderHelper helper) {
        return Notice.builder()
                     .ircParsing(helper.ircParsing())
                     .channel(helper.channelFromParameterAt(0))
                     .message(helper.lastParameter())
                     .msgId(helper.tagValue(TagKey.MSG_ID, NoticeId::getNoticeId))
                     .build();
    }
}
