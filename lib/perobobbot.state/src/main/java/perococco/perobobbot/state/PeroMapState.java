package perococco.perobobbot.state;

import com.google.common.collect.ImmutableMap;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * An immutable map with convenient methods to get a modified
 * version of it
 */
@EqualsAndHashCode(callSuper = true)
public class PeroMapState<K, V> extends PeroMapStateBase<K,V, PeroMapState<K,V>> {

    public static final PeroMapState EMPTY = new PeroMapState<>(ImmutableMap.of());

    @SuppressWarnings("unchecked")
    public static <K,V> PeroMapState<K,V> empty() {
        return EMPTY;
    }

    public PeroMapState(@NonNull ImmutableMap<K, V> content) {
        super(content, PeroMapState::new);
    }

    @Override
    protected PeroMapState<K, V> getThis() {
        return this;
    }
}
