package perobbot.twitch.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Scope;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
public enum TwitchScope implements Scope {
    //TODO
    ;

    @Getter
    private final @NonNull String name;

    public static @NonNull Stream<TwitchScope> streamValues() {
        return LazyHolder.VALUES.stream();
    }

    public static @NonNull ImmutableSet<TwitchScope> valuesAsSet() {
        return LazyHolder.VALUES;
    }


    private static class LazyHolder {
        private static final ImmutableSet<TwitchScope> VALUES = Arrays.stream(values()).collect(ImmutableSet.toImmutableSet());
    }

}
