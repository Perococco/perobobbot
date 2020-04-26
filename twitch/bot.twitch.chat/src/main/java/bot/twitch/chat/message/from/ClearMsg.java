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
@RequiredArgsConstructor
@Builder
@Getter
@ToString(exclude = "ircParsing")
public class ClearMsg extends KnownMessageFromTwitch implements ChannelSpecific {

    @NonNull
    private final IRCParsing ircParsing;

    @NonNull
    private final String login;

    @NonNull
    private final String clearedMessage;

    @NonNull
    private final String targetMsgId;

    @NonNull
    private final Channel channel;


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
