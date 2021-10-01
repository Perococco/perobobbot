package perobobbot.twitch.eventsub.api.event;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.api.UserInfo;

@Value
public class ExtensionBitsTransactionCreateEvent implements BroadcasterProvider, EventSubEvent {
    @NonNull String extensionClientId;
    @NonNull String id;
    @NonNull UserInfo broadcaster;
    @NonNull UserInfo user;
    @NonNull Product product;
}
