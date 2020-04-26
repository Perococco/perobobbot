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
@Getter
@Builder
@ToString(exclude = "ircParsing")
public class RoomState extends KnownMessageFromTwitch implements ChannelSpecific {

    @NonNull
    private final IRCParsing ircParsing;

    private final Channel channel;

    private final boolean emoteOnly;

    @NonNull
    private final FollowMode followMode;

    private final boolean r9kMode;

    private final int slow;

    private final boolean subsOnly;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.ROOMSTATE;
    }

    @Override
    public <T> T accept(@NonNull MessageFromTwitchVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @NonNull
    public static RoomState build(@NonNull AnswerBuilderHelper helper) {
        return RoomState.builder()
                        .ircParsing(helper.ircParsing())
                        .channel(helper.channelFromParameterAt(0))
                        .emoteOnly(helper.tagValueAsBoolean(TagKey.EMOTE_ONLY,false))
                        .followMode(FollowMode.create(helper.tagValueAsInt(TagKey.FOLLOWERS_ONLY, -1)))
                        .r9kMode(helper.tagValueAsBoolean(TagKey.R9K,false))
                        .slow(helper.tagValueAsInt(TagKey.SLOW, 0))
                        .subsOnly(helper.tagValueAsBoolean(TagKey.SUBS_ONLY,false))
                        .build();
    }
}
