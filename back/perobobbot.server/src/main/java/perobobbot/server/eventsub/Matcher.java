package perobobbot.server.eventsub;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.SubscriptionView;
import perobobbot.lang.SubscriptionData;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Matcher {

    public static @NonNull Match match(
            @NonNull ImmutableList<SubscriptionIdentity> onPlatform,
            @NonNull ImmutableList<SubscriptionView> persisted,
            @NonNull String webhookHost
    ) {
        return new Matcher(onPlatform, persisted,webhookHost).match();
    }

    private final @NonNull ImmutableList<SubscriptionIdentity> onPlatform;
    private final @NonNull ImmutableList<SubscriptionView> persisted;
    private final @NonNull String webhookHost;


    private Map<Key, List<SubscriptionIdentity>> validByKey;
    private Map<Key, SubscriptionView> persistedByKey;

    private Match.MatchBuilder builder = Match.builder();


    private @NonNull Match match() {
        this.dispatchPerKey();
        this.checkAllKeys();
        this.handleInvalids();
        return builder.build();
    }

    private void handleInvalids() {
        onPlatform.stream()
                  .filter(Predicate.not(SubscriptionIdentity::isValid))
                  .forEach(v -> builder.toRevokeSub(v.getSubscriptionId()));
    }

    private void dispatchPerKey() {
        this.validByKey = onPlatform.stream()
                                    .filter(SubscriptionIdentity::isValid)
                                    .collect(Collectors.groupingBy(Key::from));
        this.persistedByKey = persisted.stream()
                                       .collect(Collectors.toMap(sv -> Key.from(sv,webhookHost), s -> s));
    }

    private void checkAllKeys() {
        Stream.concat(
                validByKey.keySet().stream(),
                persistedByKey.keySet().stream()
        ).distinct().forEach(this::checkForOneData);
    }

    private void checkForOneData(@NonNull Key key) {
        final var v = validByKey.get(key);
        final var p = persistedByKey.get(key);

        final Predicate<SubscriptionIdentity> sameId = s -> s.getSubscriptionId().equals(p.getSubscriptionId());

        if (v == null && p == null) {
            return;
        }
        if (p != null && (v == null || v.isEmpty())) {
            builder.toRefreshSub(p);
            return;
        }

        if (p == null) {
            v.stream()
             .map(SubscriptionIdentity::getSubscriptionId)
             .forEach(builder::toRevokeSub);
            return;
        }

        final boolean match = v.stream().anyMatch(sameId);
        if (match) {
            v.stream()
             .filter(Predicate.not(sameId))
             .map(SubscriptionIdentity::getSubscriptionId)
             .forEach(builder::toRevokeSub);
        } else {
            builder.toUpdateSub(p.getId(), v.get(0).getSubscriptionId());
            v.stream()
             .skip(1)
             .map(SubscriptionIdentity::getSubscriptionId)
             .forEach(builder::toRevokeSub);
        }

    }

    @Value
    private static class Key {

        public static @NonNull Key from(@NonNull SubscriptionIdentity subscriptionIdentity) {
            return new Key(subscriptionIdentity.createData(), URI.create(subscriptionIdentity.getCallbackUrl()).getHost());
        }

        public static @NonNull Key from(@NonNull SubscriptionView subscriptionView, @NonNull String host) {
            return new Key(subscriptionView.createData(), host);
        }

        @NonNull SubscriptionData subscriptionData;
        @NonNull String callbackHost;
    }

}
