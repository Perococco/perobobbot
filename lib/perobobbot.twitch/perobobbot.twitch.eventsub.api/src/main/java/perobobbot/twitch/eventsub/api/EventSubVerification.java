package perobobbot.twitch.eventsub.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class EventSubVerification  implements EvenSubRequest {

    @NonNull String challenge;
    @NonNull TwitchSubscription subscription;

    @Override
    public void accept(@NonNull Visitor visitor) {
        visitor.visit(this);
    }
}
