package perobobbot.lang;

import lombok.Getter;
import lombok.NonNull;

public class NoCredentialForChatConnection extends PerobobbotException {

    @Getter
    private final @NonNull Bot bot;
    @Getter
    private final @NonNull Platform platform;

    public NoCredentialForChatConnection(@NonNull Bot bot, @NonNull Platform platform) {
        super("No credentials available for bot '"+bot.getName()+"' for platform '"+platform+"'");
        this.bot = bot;
        this.platform = platform;
    }
}
