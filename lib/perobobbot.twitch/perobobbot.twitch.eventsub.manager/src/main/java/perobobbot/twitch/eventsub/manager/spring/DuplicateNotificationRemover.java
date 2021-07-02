package perobobbot.twitch.eventsub.manager.spring;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.lang.Nil;
import perobobbot.lang.chain.Chain;
import perobobbot.lang.chain.Link;
import perobobbot.twitch.eventsub.api.EventSubNotification;
import perobobbot.twitch.eventsub.api.EventSubRequest;
import perobobbot.twitch.eventsub.api.TwitchRequestContent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class DuplicateNotificationRemover implements Link<TwitchRequestContent<EventSubRequest>, Nil> {

    private final Map<String,String> seenMessageIdsBySubscriptionId = Collections.synchronizedMap(new HashMap<>());


    @Override
    public @NonNull Nil call(@NonNull TwitchRequestContent<EventSubRequest> parameter, @NonNull Chain<TwitchRequestContent<EventSubRequest>,Nil> chain) {
        final var shouldCallNext = shouldCallNext(parameter.content(),parameter.messageId());
        if (shouldCallNext) {
            return chain.callNext(parameter);
        }
        return Nil.NIL;
    }

    private boolean alreadySeen(EventSubNotification notification, @NonNull String messageId) {
        final var subscriptionId = notification.getSubscription().getId();
        final var oldMessageId = seenMessageIdsBySubscriptionId.put(subscriptionId,messageId);
        final var alreadySeen = messageId.equals(oldMessageId);

        if (alreadySeen) {
            LOG.debug("Message with id {} already seen for subscription {}", messageId,
                      notification.getSubscription().getType());
            return true;
        }
        return false;
    }

    private boolean shouldCallNext(@NonNull EventSubRequest eventSubRequest, @NonNull String messageId) {
        if (eventSubRequest instanceof EventSubNotification notification) {
            return alreadySeen(notification, messageId);
        }
        return false;
    }

}
