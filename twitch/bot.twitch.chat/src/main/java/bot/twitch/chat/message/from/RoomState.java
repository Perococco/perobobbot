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
@Getter
@Builder
public class RoomState implements KnownMessageFromTwitch {

    @NonNull
    private final IRCParsing ircParsing;

    private final Channel channel;

    private final boolean emoteOnly;

    private final boolean followersOnly;

    private final boolean r9kMode;

    private final int slow;

    private final boolean subsOnly;

    @Override
    public @NonNull IRCCommand command() {
        return IRCCommand.ROOMSTATE;
    }

    @NonNull
    public static RoomState build(@NonNull AnswerBuilderHelper helper) {
        return RoomState.builder()
                        .ircParsing(helper.ircParsing())
                        .channel(helper.channelFormParameterAt(0))
                        .emoteOnly(helper.tagBooleanValue(TagKey.EMOTE_ONLY))
                        .followersOnly(helper.tagBooleanValue(TagKey.FOLLOWERS_ONLY))
                        .r9kMode(helper.tagBooleanValue(TagKey.R9K))
                        .slow(helper.intTagValue(TagKey.SLOW,0))
                        .subsOnly(helper.tagBooleanValue(TagKey.SUBS_ONLY))
                        .build();
    }
}
