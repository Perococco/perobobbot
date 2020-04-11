package bot.twitch.chat.message;

import bot.common.lang.Secret;
import lombok.NonNull;

/**
 * @author perococco
 **/
public class PassCommand extends TwitchCommand {

    @NonNull
    private final Secret oauthValue;

    public PassCommand(@NonNull Secret oauthValue) {
        super(IRCCommand.PASS);
        this.oauthValue = oauthValue;
    }

    @Override
    public @NonNull String payload() {
        return "PASS oauth:"+oauthValue.value();
    }
}
