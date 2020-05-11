package perobobbot.twitch.chat.message.to;

import perobobbot.chat.advanced.DispatchContext;
import perobobbot.common.lang.Secret;
import perobobbot.twitch.chat.message.IRCCommand;
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
