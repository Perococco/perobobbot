package bot.twitch.chat.event;

import lombok.NonNull;

public class Disconnection implements TwitchChatEvent {

    private static final Disconnection DISCONNECTION = new Disconnection();

    @NonNull
    public static Disconnection create() {
        return DISCONNECTION;
    }

    private Disconnection() {}

}
