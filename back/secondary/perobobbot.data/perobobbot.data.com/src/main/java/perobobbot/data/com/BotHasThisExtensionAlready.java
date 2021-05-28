package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;

import java.util.UUID;

public class BotHasThisExtensionAlready extends DataException {

    @Getter
    private final @NonNull UUID botId;

    @Getter
    private final @NonNull UUID extensionId;

    public BotHasThisExtensionAlready(@NonNull UUID botId, @NonNull UUID extensionId) {
        super("The extension is already associated with this bot");
        this.botId = botId;
        this.extensionId = extensionId;
    }
}
