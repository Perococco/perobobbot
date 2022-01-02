package perobobbot.server.eventsub;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
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
    private final @NonNull ImmutableList<SubscriptionView> onBot;


    private Map<Key, List<SubscriptionIdentity>> validOnPlatformPerKey;
    private Map<Key, SubscriptionView> onBotPerKey;

    private Match.MatchBuilder builder = Match.builder();


    private @NonNull Match match() {
//        this.display();
        this.dispatchPerKey();
        this.checkAllKeys();
        this.handleInvalids();
        return builder.build();
    }

    private void display() {
        System.out.println("ON PLATFORM");
        onPlatform.forEach(s -> System.out.println(s.getSubscriptionId()+ "  " + s.getCallbackUrl()));
        System.out.println("ON BOT");
        onBot.forEach(s -> System.out.println(s.getSubscriptionId() + "  "+s.getCallbackUrl()));
    }

    private void handleInvalids() {
        onPlatform.stream()
                  .filter(Predicate.not(SubscriptionIdentity::isValid))
                  .forEach(v -> builder.toRevokeSub(v.getSubscriptionId()));
    }

    private void dispatchPerKey() {
        this.validOnPlatformPerKey = onPlatform.stream()
                                               .filter(SubscriptionIdentity::isValid)
                                               .collect(Collectors.groupingBy(Key::from));

        this.onBotPerKey = onBot.stream()
                                .collect(Collectors.toMap(Key::from, s -> s));
    }

    private void checkAllKeys() {
        Stream.concat(
                validOnPlatformPerKey.keySet().stream(),
                onBotPerKey.keySet().stream()
        ).distinct().forEach(this::checkForOneData);
    }

    private void checkForOneData(@NonNull Key key) {
        final var listOfValidOnPlatform = validOnPlatformPerKey.get(key);
        final var onBot = onBotPerKey.get(key);

        final Predicate<SubscriptionIdentity> sameId = s -> s.getSubscriptionId().equals(onBot.getSubscriptionId());

        if (listOfValidOnPlatform == null && onBot == null) {
            return;
        }

        if (onBot != null && (listOfValidOnPlatform == null || listOfValidOnPlatform.isEmpty())) {
            builder.toRefreshSub(onBot);
            return;
        }

        if (onBot == null) {
            listOfValidOnPlatform.stream()
             .map(SubscriptionIdentity::getSubscriptionId)
             .forEach(builder::toRevokeSub);
            return;
        }

        final boolean match = listOfValidOnPlatform.stream().anyMatch(sameId);
        if (match) {
            listOfValidOnPlatform.stream()
             .filter(Predicate.not(sameId))
             .map(SubscriptionIdentity::getSubscriptionId)
             .forEach(builder::toRevokeSub);
        } else {
            builder.toUpdateSub(onBot.getId(), listOfValidOnPlatform.get(0));
            listOfValidOnPlatform.stream()
             .skip(1)
             .map(SubscriptionIdentity::getSubscriptionId)
             .forEach(builder::toRevokeSub);
        }

    }

    @Value
    private static class Key {

        public static @NonNull Key from(@NonNull SubscriptionIdentity subscriptionIdentity) {
            return new Key(subscriptionIdentity.createData(), subscriptionIdentity.getCallbackUrl());
        }

        public static @NonNull Key from(@NonNull SubscriptionView subscriptionView) {
            return new Key(subscriptionView.createData(), subscriptionView.getCallbackUrl());
        }

        @NonNull SubscriptionData subscriptionData;
        @NonNull String callbackUrl;
    }

}
