package bot.twitch.chat.message;

import lombok.NonNull;

/**
 * @author perococco
 **/
public class NickRequest extends TwitchRequest<OAuthResult> {

    @NonNull
    private final String nickname;

    public NickRequest(@NonNull String nickname) {
        super(IRCCommand.NICK, OAuthResult.class);
        this.nickname = nickname;
    }

    @Override
    public @NonNull String payload() {
        return "NICK "+nickname;
    }
}
