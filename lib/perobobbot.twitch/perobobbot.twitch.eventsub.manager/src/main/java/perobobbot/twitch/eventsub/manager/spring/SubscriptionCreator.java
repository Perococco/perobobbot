package perobobbot.twitch.eventsub.manager.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Todo;
import perobobbot.lang.chain.Chain;
import perobobbot.lang.chain.Link;
import perobobbot.twitch.eventsub.api.SubscriptionWithLogin;
import perobobbot.twitch.eventsub.manager.ToTwitchEventSubRequest;

@RequiredArgsConstructor
public class SubscriptionCreator implements Link<SubscriptionWithLogin> {

    private final @NonNull ToTwitchEventSubRequest toTwitchEventSubRequest;

    @Override
    public void call(@NonNull SubscriptionWithLogin parameter, @NonNull Chain<SubscriptionWithLogin> chain) {
        //TODO retrieve subscription with same type and condition.
        // 1- if some exist, simply add the (login,subscription) to the database
        // 2- otherwise send the request to Twitch and add (login,subscription) to the database on success
        //
        Todo.TODO();
    }
}
