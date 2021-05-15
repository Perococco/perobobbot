package perobbot.twitch.oauth;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Scope;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public enum TwitchScope implements Scope {
    ANALYTICS_READ_EXTENSIONS("analytics:read:extensions"),
    ANALYTICS_READ_GAMES("analytics:read:games"),
    BITS_READ("bits:read"),
    CHANNEL_EDIT_COMMERCIAL("channel:edit:commercial"),
    CHANNEL_MANAGE_BROADCAST("channel:manage:broadcast"),
    CHANNEL_MANAGE_EXTENSIONS("channel:manage:extensions"),
    CHANNEL_MANAGE_POLLS("channel:manage:polls"),
    CHANNEL_MANAGE_PREDICTIONS("channel:manage:predictions"),
    CHANNEL_MANAGE_REDEMPTIONS("channel:manage:redemptions"),
    CHANNEL_MANAGE_VIDEOS("channel:manage:videos"),
    CHANNEL_READ_EDITORS("channel:read:editors"),
    CHANNEL_READ_HYPE_TRAIN("channel:read:hype_train"),
    CHANNEL_READ_POLLS("channel:read:polls"),
    CHANNEL_READ_PREDICTIONS("channel:read:predictions"),
    CHANNEL_READ_REDEMPTIONS("channel:read:redemptions"),
    CHANNEL_READ_STREAM_KEY("channel:read:stream_key"),
    CHANNEL_READ_SUBSCRIPTIONS("channel:read:subscriptions"),
    CLIPS_EDIT("clips:edit"),
    MODERATION_READ("moderation:read"),
    MODERATOR_MANAGE_AUTOMOD("moderator:manage:automod"),
    USER_EDIT("user:edit"),
    USER_EDIT_FOLLOWS("user:edit:follows"),
    USER_MANAGE_BLOCKED_USERS("user:manage:blocked_users"),
    USER_READ_BLOCKED_USERS("user:read:blocked_users"),
    USER_READ_BROADCAST("user:read:broadcast"),
    USER_READ_FOLLOWS("user:read:follows"),
    USER_READ_SUBSCRIPTIONS("user:read:subscriptions"),
    ;

    @Getter
    private final @NonNull String name;

    public static @NonNull Stream<TwitchScope> streamValues() {
        return LazyHolder.VALUES.stream();
    }

    public static @NonNull Optional<TwitchScope> findScopeByName(@NonNull String twitchScopeName) {
        return Optional.ofNullable(LazyHolder.VALUES_BY_NAME.get(twitchScopeName));
    }

    public static @NonNull ImmutableSet<TwitchScope> valuesAsSet() {
        return LazyHolder.VALUES;
    }


    private static class LazyHolder {
        private static final ImmutableMap<String, TwitchScope> VALUES_BY_NAME = Arrays.stream(values()).collect(ImmutableMap.toImmutableMap(
                Enum::name, s -> s));
        private static final ImmutableSet<TwitchScope> VALUES = Arrays.stream(values()).collect(ImmutableSet.toImmutableSet());
    }


}
