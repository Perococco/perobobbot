package perobobbot.data.com;

import lombok.NonNull;

import java.util.UUID;

public class BotHasThisExtensionAlready extends DataException {

    private final @NonNull UUID botId;

    private final @NonNull UUID extensionId;

    public BotHasThisExtensionAlready(@NonNull UUID botId, @NonNull UUID extensionId) {
        super("The extension is already associated with this bot");
        this.botId = botId;
        this.extensionId = extensionId;
    }
}
