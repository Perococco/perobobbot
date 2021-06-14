package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;

@Value
public class ExtensionBitsTransactionCreateEvent implements EventSubEvent {
    @NonNull String extensionClientId;
    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
    @NonNull Product product;
}
