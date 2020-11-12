package perobobbot.twitch.chat.message.to;

import lombok.NonNull;
import perobobbot.lang.CastTool;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.fp.TryResult;
import perobobbot.twitch.chat.TwitchChatAuthenticationFailure;
import perobobbot.twitch.chat.TwitchChatState;
import perobobbot.twitch.chat.TwitchConstants;
import perobobbot.twitch.chat.message.IRCCommand;
import perobobbot.twitch.chat.message.from.GlobalUserState;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.from.Notice;

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
    public @NonNull String payload(@NonNull DispatchContext dispatchContext) {
        return "NICK " + nickname;
    }

    @Override
    public @NonNull Optional<TryResult<Throwable, GlobalUserState>> isMyAnswer(
            @NonNull MessageFromTwitch messageFromTwitch,
            @NonNull TwitchChatState state) {
        return CastTool.castAndCall(messageFromTwitch,
                                    GlobalUserState.class,
                                    this::checkGlobalUserState)
                       .or(() -> CastTool.castAndCall(messageFromTwitch, Notice.class, this::checkNotice));
    }

    @NonNull
    private Optional<TryResult<Throwable, GlobalUserState>> checkGlobalUserState(@NonNull GlobalUserState globalUserState) {
        return Optional.of(TryResult.success(globalUserState));
    }

    @NonNull
    private Optional<TryResult<Throwable, GlobalUserState>> checkNotice(@NonNull Notice notice) {
        if (Stream.of(TwitchConstants.CHAT_LOGIN_AUTHENTICATION_FAILED,
                      TwitchConstants.CHAT_IMPROPERLY_FORMATTED_AUTH)
                  .anyMatch(m -> notice.getMessage().equals(m))) {
            return Optional.of(TryResult.failure(new TwitchChatAuthenticationFailure(notice.getMessage())));
        }
        return Optional.empty();
    }
}
