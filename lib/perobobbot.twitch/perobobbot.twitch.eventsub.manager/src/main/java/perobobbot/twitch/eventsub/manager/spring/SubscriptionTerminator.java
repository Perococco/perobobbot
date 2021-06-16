package perobobbot.twitch.eventsub.manager.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Todo;
import perobobbot.lang.chain.Chain;
import perobobbot.lang.chain.Link;
import perobobbot.twitch.eventsub.api.SubscriptionWithLogin;
import perobobbot.twitch.eventsub.manager.ToTwitchEventSubRequest;

@RequiredArgsConstructor
public class SubscriptionTerminator implements Link<SubscriptionWithLogin> {


    private final @NonNull ToTwitchEventSubRequest toTwitchEventSubRequest;

    @Override
    public void call(@NonNull SubscriptionWithLogin parameter, @NonNull Chain<SubscriptionWithLogin> chain) {
        //TODO
        // 1- Remove (login,subscripion) from database.
        // 2- If no entry is present in database for (subscription.type, subscription.condition) perform request to Twitch
        //    to delete the subscription
        Todo.TODO();
    }
}
