package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

public class UnknownBot extends DataException {

    @NonNull
    @Getter
    private final UUID botId;

    public UnknownBot(@NonNull UUID botId) {
        super("Could not find any bot with id='"+botId+"'");
        this.botId = botId;
    }
}
