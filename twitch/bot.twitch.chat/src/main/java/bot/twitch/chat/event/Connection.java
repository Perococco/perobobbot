package bot.twitch.chat.event;

import lombok.NonNull;

public class Connection implements TwitchChatEvent {

    private static final Connection CONNECTION = new Connection();

    @NonNull
    public static Connection create() {
        return CONNECTION;
    }

    private Connection() {}

}
