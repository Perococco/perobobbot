package perobobbot.twitch.chat.message.to;

import lombok.NonNull;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.fp.TryResult;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.ChannelSpecific;
import perobobbot.twitch.chat.JoinFailure;
import perobobbot.twitch.chat.TwitchChatState;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.from.Notice;
import perobobbot.twitch.chat.message.from.NoticeId;
import perobobbot.twitch.chat.message.from.UserState;

import java.util.Optional;

/**
 * @author perococco
 **/
public class Join extends RequestToTwitch<UserState> {

    @NonNull
    private final Channel channel;

    public Join(@NonNull Channel channel) {
        super(IRCCommand.JOIN, UserState.class);
        this.channel = channel;
    }

    @Override
    @NonNull
    public String payload(@NonNull DispatchContext dispatchContext) {
        return "JOIN #"+channel.getName();
    }

    @Override
    public @NonNull Optional<TryResult<Throwable, UserState>> isMyAnswer(@NonNull MessageFromTwitch messageFromTwitch, @NonNull TwitchChatState state) {
        if (appliesToMyChannel(messageFromTwitch)) {

            if (messageFromTwitch instanceof Notice) {
                return checkNotice((Notice) messageFromTwitch);
            }

            if (messageFromTwitch instanceof UserState) {
                return checkUserState((UserState) messageFromTwitch);
            }
        }
        return Optional.empty();
    }

    private boolean appliesToMyChannel(MessageFromTwitch messageFromTwitch) {
        if (messageFromTwitch instanceof ChannelSpecific) {
            final ChannelSpecific channelSpecific = ((ChannelSpecific) messageFromTwitch);
            return channelSpecific.getChannel().equals(channel);
        }
        return false;
    }

    @NonNull
    private Optional<TryResult<Throwable, UserState>> checkNotice(@NonNull Notice notice) {
        if (notice.getMsgId() == NoticeId.MSG_CHANNEL_SUSPENDED) {
            return Optional.of(TryResult.failure(new JoinFailure(notice.getMsgId(), notice.getMessage())));
        }
        return Optional.empty();
    }

    @NonNull
    private Optional<TryResult<Throwable, UserState>> checkUserState(@NonNull UserState userState) {
        return Optional.of(TryResult.success(userState));
    }

}
