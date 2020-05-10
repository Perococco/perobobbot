package bot.twitch.chat.message.to;

import bot.chat.advanced.DispatchContext;
import bot.common.lang.Secret;
import bot.twitch.chat.message.IRCCommand;
import lombok.NonNull;

/**
 * @author perococco
 **/
public class Pass extends CommandToTwitch {

    @NonNull
    private final Secret oauthValue;

    public Pass(@NonNull Secret oauthValue) {
        super(IRCCommand.PASS);
        this.oauthValue = oauthValue;
    }

    @Override
    public @NonNull String payload(@NonNull DispatchContext dispatchContext) {
        return "PASS oauth:" + oauthValue.getValue();
    }

}
