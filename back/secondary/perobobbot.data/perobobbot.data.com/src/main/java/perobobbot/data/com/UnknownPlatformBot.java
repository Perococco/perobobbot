package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

public class UnknownPlatformBot extends DataException {

    @NonNull
    @Getter
    private final UUID platformBotId;

    public UnknownPlatformBot(@NonNull UUID platformBotId) {
        super("Could not find any platform bot with id='"+platformBotId+"'");
        this.platformBotId = platformBotId;
    }
}
