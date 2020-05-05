package bot.twitch.chat.message.from;

import bot.common.irc.IRCParsing;
import bot.twitch.chat.Channel;
import bot.twitch.chat.ChannelSpecific;
import bot.twitch.chat.message.IRCCommand;
import bot.twitch.chat.message.TagKey;
import lombok.*;

/**
 * @author perococco
 **/
@Getter
@ToString
public class ClearMsg extends KnownMessageFromTwitch implements ChannelSpecific {


    @NonNull
    private final String login;

    @NonNull
    private final String clearedMessage;

    @NonNull
    private final String targetMsgId;

    @NonNull
    private final Channel channel;

    @Builder
    public ClearMsg(@NonNull IRCParsing ircParsing, @NonNull String login, @NonNull String clearedMessage, @NonNull String targetMsgId, @NonNull Channel channel) {
        super(ircParsing);
        this.login = login;
        this.clearedMessage = clearedMessage;
        this.targetMsgId = targetMsgId;
        this.channel = channel;
    }

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.CLEARMSG;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public static @NonNull ClearMsg build(@NonNull AnswerBuilderHelper helper) {
        return ClearMsg.builder()
                       .ircParsing(helper.ircParsing())
                       .channel(helper.channelFromParameterAt(0))
                       .clearedMessage(helper.lastParameter())
                       .login(helper.tagValue(TagKey.LOGIN))
                       .targetMsgId(helper.tagValue(TagKey.TARGET_MSG_ID))
                       .build();
    }

}
