package perobobbot.spring;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.util.ConcurrentReferenceHashMap;
import perobobbot.lang.ObjectPredicate;
import perobobbot.lang.ThrowableTool;

import java.util.concurrent.Callable;

/**
 * Cache using Soft reference to store cached value to allow memory caching
 * A special handling of eviction is also used. If the eviction key is of type {@link ObjectPredicate} then
 * all keys matching the predicate are removed.
 */
@Log4j2
@RequiredArgsConstructor
public class SoftReferenceCache implements Cache {

    @NonNull
    @Getter
    private final String name;

    @NonNull
    private final ConcurrentReferenceHashMap<Object,Object> store;


    public SoftReferenceCache(@NonNull String name) {
        this(name, new ConcurrentReferenceHashMap<>(256, ConcurrentReferenceHashMap.ReferenceType.SOFT));
    }

    @NonNull
    @Override
    public final ConcurrentReferenceHashMap<Object, Object> getNativeCache() {
        return this.store;
    }

    @Override
    public ValueWrapper get(@NonNull Object key) {
        final Object value = store.get(key);
        return value == null ? null : new SimpleValueWrapper(value);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(@NonNull Object key, Class<T> type) {
        final Object value = store.get(key);
        if (value != null && type != null && !type.isInstance(value)) {
            throw new IllegalStateException(
                    "Cached value is not of required type [" + type.getName() + "]: " + value);
        }
        return (T) value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T get(@NonNull Object key, @NonNull Callable<T> valueLoader) {
        return (T)this.store.computeIfAbsent(key, k -> {
            try {
                return valueLoader.call();
            } catch (Throwable e) {
                ThrowableTool.interruptThreadIfCausedByInterruption(e);
                throw new ValueRetrievalException(key,valueLoader,e);
            }
        });
    }

    @Override
    public void put(@NonNull Object key, Object value) {
        if (value == null) {
            LOG.warn("Trying to put null value in cache for key='"+key+"'");
            return;
        }
        store.put(key, value);
    }

    @Override
    public void evict(@NonNull Object key) {
        if (key instanceof ObjectPredicate) {
            store.keySet().removeIf(((ObjectPredicate) key));
        }
        else {
            store.remove(key);
        }
    }

    @Override
    public void clear() {
        store.clear();
    }

    public void cleanUp() {
        store.purgeUnreferencedEntries();
    }

}
