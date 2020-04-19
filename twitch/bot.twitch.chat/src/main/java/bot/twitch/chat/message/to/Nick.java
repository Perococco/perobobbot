package bot.twitch.chat.message.to;

import bot.common.lang.CastTool;
import bot.common.lang.fp.TryResult;
import bot.twitch.chat.TwitchChatAuthenticationFailure;
import bot.twitch.chat.message.IRCCommand;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.message.from.GlobalUserState;
import bot.twitch.chat.message.from.Notice;
import bot.twitch.chat.message.from.MessageFromTwitch;
import lombok.NonNull;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author perococco
 **/
public class Nick extends RequestToTwitch<GlobalUserState> {

    @NonNull
    private final String nickname;

    public Nick(@NonNull String nickname) {
        super(IRCCommand.NICK, GlobalUserState.class);
        this.nickname = nickname;
    }

    @Override
    public @NonNull String payload() {
        return "NICK " + nickname;
    }

    @Override
    public @NonNull Optional<TryResult<Throwable,GlobalUserState>> isMyAnswer(
            @NonNull MessageFromTwitch messageFromTwitch,
            @NonNull TwitchChatState state) {
        return CastTool.castAndCheck(messageFromTwitch, GlobalUserState.class, this::checkGlobalUserState)
                       .or(() -> CastTool.castAndCheck(messageFromTwitch, Notice.class, this::checkNotice));
    }

    @NonNull
    private Optional<TryResult<Throwable,GlobalUserState>> checkGlobalUserState(@NonNull GlobalUserState globalUserState) {
        return Optional.of(TryResult.success(globalUserState));
    }

    @NonNull
    private Optional<TryResult<Throwable,GlobalUserState>> checkNotice(@NonNull Notice notice) {
        if (Stream.of("Login authentication failed",
                      "Improperly formatted auth")
                  .anyMatch(m -> notice.message().equals(m))) {
            return Optional.of(TryResult.failure(new TwitchChatAuthenticationFailure(notice.message())));
        }
        return Optional.empty();
    }
}
