package perobobbot.server.eventsub;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.SubscriptionView;
import perobobbot.lang.Platform;
import perobobbot.lang.fp.Value2;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Matcher {

    public static @NonNull Match match(
            @NonNull ImmutableList<SubscriptionIdentity> valid,
            @NonNull ImmutableList<SubscriptionView> persisted
    ) {
        return new Matcher(valid, persisted).match();
    }

    private final @NonNull ImmutableList<SubscriptionIdentity> valid;
    private final @NonNull ImmutableList<SubscriptionView> persisted;


    private Map<Key, List<SubscriptionIdentity>> validByKey;
    private Map<Key, SubscriptionView> persistedByKey;

    private Match.MatchBuilder builder = Match.builder();


    private @NonNull Match match() {
        this.dispatchPerKey();
        this.checkAllKeys();
        return builder.build();
    }

    private void dispatchPerKey() {
        this.validByKey = valid.stream().collect(Collectors.groupingBy(Key::from));
        this.persistedByKey = persisted.stream().collect(Collectors.toMap(Key::from, s -> s));
    }

    private void checkAllKeys() {
        Stream.concat(
                validByKey.keySet().stream(),
                persistedByKey.keySet().stream()
        ).distinct().forEach(this::checkKey);
    }

    private void checkKey(@NonNull Key key) {
        final var v = validByKey.get(key);
        final var p = persistedByKey.get(key);

        final Predicate<SubscriptionIdentity> sameId = s -> s.subscriptionId().equals(p.subscriptionId());

        if (v == null && p == null) {
            return;
        }
        if (p != null && (v == null || v.isEmpty())) {
            builder.toRefreshSub(p.id());
            return;
        }

        if (p == null) {
            v.stream()
             .map(SubscriptionIdentity::subscriptionId)
             .forEach(builder::toRevokeSub);
            return;
        }

        final boolean match = v.stream().anyMatch(sameId);
        if (match) {
            v.stream()
             .filter(Predicate.not(sameId))
             .map(SubscriptionIdentity::subscriptionId)
             .forEach(builder::toRevokeSub);
        } else {
            builder.toUpdateSub(p.id(), v.get(0).subscriptionId());
            v.stream()
             .skip(1)
             .map(SubscriptionIdentity::subscriptionId)
             .forEach(builder::toRevokeSub);
        }

    }


    private record Key(@NonNull Platform platform, @NonNull String subscriptionType, @NonNull String conditionId) {

        public static Key from(@NonNull SubscriptionIdentity subscriptionIdentity) {
            return new Key(subscriptionIdentity.platform(), subscriptionIdentity.subscriptionType(), subscriptionIdentity.conditionId());
        }

        public static Key from(@NonNull SubscriptionView subscriptionView) {
            return new Key(subscriptionView.platform(), subscriptionView.subscriptionType(), subscriptionView.conditionId());
        }
    }

}
