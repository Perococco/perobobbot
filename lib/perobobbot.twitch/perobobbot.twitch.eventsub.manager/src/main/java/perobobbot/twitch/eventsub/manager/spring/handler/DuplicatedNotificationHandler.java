package perobobbot.twitch.eventsub.manager.spring.handler;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import perobobbot.lang.Handler;
import perobobbot.twitch.eventsub.api.EventSubNotification;
import perobobbot.twitch.eventsub.api.EventSubRequest;
import perobobbot.twitch.eventsub.api.TwitchRequestContent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class DuplicatedNotificationHandler implements Handler<TwitchRequestContent<EventSubRequest>,EventSubNotification> {

    private final Map<String,String> seenMessageIdsBySubscriptionId = Collections.synchronizedMap(new HashMap<>());

    @Override
    public @NonNull Optional<EventSubNotification> handle(@NonNull TwitchRequestContent<EventSubRequest> input) {
        if (input.content() instanceof EventSubNotification eventSubNotification) {
            if (alreadySeen(eventSubNotification,input.messageId())) {
                return Optional.empty();
            }
            return Optional.of(eventSubNotification);
        }
        return Optional.empty();
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
