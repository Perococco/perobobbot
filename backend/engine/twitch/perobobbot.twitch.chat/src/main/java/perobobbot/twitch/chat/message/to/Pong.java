package perobobbot.twitch.chat.message.to;

import perobobbot.chat.advanced.DispatchContext;
import perobobbot.twitch.chat.message.IRCCommand;
import lombok.NonNull;

public class Pong extends CommandToTwitch {

    public static Pong create() {
        return new Pong();
    }

    private Pong() {
        super(IRCCommand.PONG);
    }

    @Override
    public String commandInPayload() {
        return "PONG";
    }

    @Override
    public @NonNull String payload(@NonNull DispatchContext dispatchContext) {
        return "PONG :tmi.twitch.tv";
    }

}
