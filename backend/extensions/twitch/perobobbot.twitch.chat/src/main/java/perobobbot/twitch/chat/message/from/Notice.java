package perobobbot.twitch.chat.message.from;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import perobobbot.irc.IRCParsing;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.ChannelSpecific;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.TagKey;

/**
 * @author perococco
 **/
@Getter
public class Notice extends KnownMessageFromTwitch implements ChannelSpecific {

    @NonNull
    private final Channel channel;

    @NonNull
    private final NoticeId msgId;

    @NonNull
    private final String message;

    @Builder
    public Notice(@NonNull IRCParsing ircParsing, @NonNull Channel channel, @NonNull NoticeId msgId, @NonNull String message) {
        super(ircParsing);
        this.channel = channel;
        this.msgId = msgId;
        this.message = message;
    }

    @Override
    public @NonNull IRCCommand getCommand() {
        return IRCCommand.NOTICE;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }


    public static @NonNull Notice build(@NonNull AnswerBuilderHelper helper) {
        return Notice.builder()
                     .ircParsing(helper.getIrcParsing())
                     .channel(helper.channelFromParameterAt(0))
                     .message(helper.lastParameter())
                     .msgId(helper.tagValue(TagKey.MSG_ID, NoticeId::getNoticeId))
                     .build();
    }
}
