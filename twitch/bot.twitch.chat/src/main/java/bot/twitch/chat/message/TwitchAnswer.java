package bot.twitch.chat.message;

import lombok.*;

/**
 * @author perococco
 **/
@EqualsAndHashCode(callSuper = true)
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public abstract class TwitchAnswer extends TwitchMessage {

    @NonNull
    @Getter
    private final IRCCommand command;

    @NonNull
    @Getter
    private final String payload;

}
