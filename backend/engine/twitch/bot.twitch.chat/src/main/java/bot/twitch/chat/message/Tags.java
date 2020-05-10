package bot.twitch.chat.message;

import bot.common.irc.Tag;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author perococco
 **/
public interface Tags {

    @NonNull
    Optional<String> findTag(@NonNull TagKey tagKey);

    @NonNull
    default String getTag(@NonNull TagKey tagKey) {
        return findTag(tagKey).orElseThrow(() -> new IllegalArgumentException("Could not find tag "+tagKey));
    }

    @NonNull
    default String getTag(@NonNull TagKey tagKey, @NonNull String defaultValue) {
        return findTag(tagKey).orElse(defaultValue);
    }

    default int getIntTag(@NonNull TagKey tagKey) {
        return Integer.parseInt(getTag(tagKey));
    }

    @NonNull
    default Optional<Integer> findIntTag(@NonNull TagKey tagKey) {
        return findTag(tagKey,Integer::parseInt);
    }

    @NonNull
    default <T> Optional<T> findTag(@NonNull TagKey tagKey, @NonNull Function<? super String, ? extends T> mapper) {
        return findTag(tagKey).map(mapper);
    }

    @NonNull
    default <T> Optional<T> flatFindTag(@NonNull TagKey tagKey, @NonNull Function<? super String, ? extends Optional<? extends T>> mapper) {
        return findTag(tagKey).flatMap(mapper);
    }


    @NonNull
    static Tags mapBased(@NonNull ImmutableMap<String,Tag> tags) {
        return tagKey -> {
            final Tag tag = tags.get(tagKey.name());
            return tag == null?Optional.empty():Optional.of(tag.getValue());
        };
    }

}
