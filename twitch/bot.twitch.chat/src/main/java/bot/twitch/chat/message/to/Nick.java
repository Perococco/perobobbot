package bot.twitch.chat.message.to;

import bot.common.lang.CastTool;
import bot.twitch.chat.message.IRCCommand;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.message.from.Notice;
import bot.twitch.chat.message.from.MessageFromTwitch;
import bot.twitch.chat.message.from.Welcome;
import lombok.NonNull;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author perococco
 **/
public class Nick extends RequestToTwitch<OAuthResult> {

    @NonNull
    private final String nickname;

    public Nick(@NonNull String nickname) {
        super(IRCCommand.NICK, OAuthResult.class);
        this.nickname = nickname;
    }

    @Override
    public @NonNull String payload() {
        return "NICK " + nickname;
    }

    @Override
    public @NonNull Optional<OAuthResult> isAnswer(
            @NonNull MessageFromTwitch messageFromTwitch,
            @NonNull TwitchChatState state) {
        return CastTool.castAndCheck(messageFromTwitch, Welcome.class, this::checkWelcome)
                       .or(() -> CastTool.castAndCheck(messageFromTwitch, Notice.class, this::checkNotice));
    }

    @NonNull
    private Optional<OAuthResult> checkWelcome(@NonNull Welcome welcome) {
        return Optional.of(OAuthResult.success());
    }

    @NonNull
    private Optional<OAuthResult> checkNotice(@NonNull Notice notice) {
        if (Stream.of("Login authentication failed",
                      "Improperly formatted auth")
                  .anyMatch(m -> notice.message().equals(m))) {
            return Optional.of(OAuthResult.failure(notice.message()));
        }
        return Optional.empty();
    }
}
