package bot.twitch.chat.message.to;

import bot.chat.advanced.DispatchContext;
import bot.common.lang.fp.TryResult;
import bot.twitch.chat.Channel;
import bot.twitch.chat.ChannelSpecific;
import bot.twitch.chat.JoinFailure;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.message.IRCCommand;
import bot.twitch.chat.message.from.*;
import lombok.NonNull;

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
        return "JOIN #"+channel.name();
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
            return channelSpecific.channel().equals(channel);
        }
        return false;
    }

    @NonNull
    private Optional<TryResult<Throwable, UserState>> checkNotice(@NonNull Notice notice) {
        if (notice.msgId() == NoticeId.MSG_CHANNEL_SUSPENDED) {
            return Optional.of(TryResult.failure(new JoinFailure(notice.msgId(), notice.message())));
        }
        return Optional.empty();
    }

    @NonNull
    private Optional<TryResult<Throwable, UserState>> checkUserState(@NonNull UserState userState) {
        return Optional.of(TryResult.success(userState));
    }

}
