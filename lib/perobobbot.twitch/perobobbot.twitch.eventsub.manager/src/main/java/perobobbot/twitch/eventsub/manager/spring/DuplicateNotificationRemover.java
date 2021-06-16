package perobobbot.twitch.eventsub.manager.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.lang.chain.Chain;
import perobobbot.lang.chain.Link;
import perobobbot.twitch.eventsub.api.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class DuplicateNotificationRemover implements Link<TwitchRequestContent<EventSubRequest>> {

    private final Map<String,String> seenMessageIdsBySubscriptionId = Collections.synchronizedMap(new HashMap<>());


    @Override
    public void call(@NonNull TwitchRequestContent<EventSubRequest> parameter, @NonNull Chain<TwitchRequestContent<EventSubRequest>> chain) {
        final var callNext= parameter.content().accept(new CallNextPredicate(parameter.messageId()));
        if (Boolean.TRUE.equals(callNext)) {
            chain.callNext(parameter);
        }
    }

    private boolean notAlreadySeen(@NonNull String messageId, EventSubNotification notification) {
        final var subscriptionId = notification.getSubscription().getId();
        final var oldMessageId = seenMessageIdsBySubscriptionId.put(subscriptionId,messageId);

        final var alreadySeen = messageId.equals(oldMessageId);

        if (alreadySeen) {
            LOG.debug("Message with id {} already seen for subscription {}", messageId,
                      notification.getSubscription().getType());
            return false;
        }
        return true;
    }


    @RequiredArgsConstructor
    private class CallNextPredicate implements EventSubRequest.Visitor<Boolean> {

        private final @NonNull String messageId;

        @Override
        public @NonNull Boolean visit(@NonNull EventSubNotification notification) {
            return notAlreadySeen(messageId,notification);
        }


        @Override
        public @NonNull Boolean visit(@NonNull EventSubRevocation revocation) {
            return true;
        }

        @Override
        public @NonNull Boolean visit(@NonNull EventSubVerification verification) {
            return true;
        }
    }
}
