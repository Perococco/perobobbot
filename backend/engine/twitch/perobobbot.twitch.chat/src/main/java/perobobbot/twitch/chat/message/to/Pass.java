package perobobbot.twitch.chat.message.to;

import lombok.NonNull;
import perobobbot.common.lang.DispatchContext;
import perobobbot.common.lang.Secret;
import perobobbot.twitch.chat.message.IRCCommand;

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
