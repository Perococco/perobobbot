package perobobbot.chat.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import perobobbot.lang.Bot;
import perobobbot.lang.Platform;

@Getter
@ToString
public class ChatChannelNotJoined extends ChatException {

    private final @NonNull Bot bot;
    private final @NonNull Platform platform;
    private final @NonNull String channelName;

    public ChatChannelNotJoined(
            @NonNull Bot bot,
            @NonNull Platform platform,
            @NonNull String channelName) {
        super("The bot '"+bot.getName()+"' has not joined the channel '"+channelName+"' on platfrom '"+platform+"'");
        this.platform = platform;
        this.bot = bot;
        this.channelName = channelName;
    }
}
