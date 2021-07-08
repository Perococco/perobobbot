package perobobbot.server.eventsub;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.data.com.SubscriptionIdentity;
import perobobbot.data.com.SubscriptionView;
import perobobbot.lang.SubscriptionData;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Matcher {

    public static @NonNull Match match(
            @NonNull ImmutableList<SubscriptionIdentity> onPlatform,
            @NonNull ImmutableList<SubscriptionView> persisted
    ) {
        return new Matcher(onPlatform, persisted).match();
    }

    private final @NonNull ImmutableList<SubscriptionIdentity> onPlatform;
    private final @NonNull ImmutableList<SubscriptionView> persisted;


    private Map<SubscriptionData, List<SubscriptionIdentity>> validByKey;
    private Map<SubscriptionData, SubscriptionView> persistedByKey;

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
        this.validByKey = onPlatform.stream().filter(SubscriptionIdentity::isValid).collect(Collectors.groupingBy(SubscriptionIdentity::createData));
        this.persistedByKey = persisted.stream().collect(Collectors.toMap(SubscriptionView::createData, s -> s));
    }

    private void checkAllKeys() {
        Stream.concat(
                validByKey.keySet().stream(),
                persistedByKey.keySet().stream()
        ).distinct().forEach(this::checkForOneData);
    }

    private void checkForOneData(@NonNull SubscriptionData data) {
        final var v = validByKey.get(data);
        final var p = persistedByKey.get(data);

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

}
