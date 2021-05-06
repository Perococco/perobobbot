package perobobbot.lang;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

@Log4j2
public class IdMap<K,T> {

    private final @NonNull Set<IdKey<K>> keys = new HashSet<>();

    private final @NonNull Map<IdKey<K>,T> data = new HashMap<>();

    public @NonNull IdBooking bookNewId(@NonNull K userKey) {
        do {
            final var id = new IdKey<>(userKey, RandomString.generate(32));
            if (keys.add(id)) {
                return new IdBooking(id);
            }
        } while (true);
    }

    private void freeId(@NonNull IdKey<K> key) {
        this.keys.remove(key);
        Optional.ofNullable(data.remove(key))
                .flatMap(CastTool.caster(Disposable.class))
                .ifPresent(Disposable::dispose);
    }

    private void dispose(@NonNull T data) {
        if (data instanceof Disposable) {
            try {
                ((Disposable) data).dispose();
            } catch (Throwable t) {
                ThrowableTool.interruptThreadIfCausedByInterruption(t);
                LOG.warn("An error occurred while disposing data {} ",data);
                LOG.debug(t);
            }
        }
    }

    public @NonNull Optional<T> remove(@NonNull IdKey<K> id) {
        return Optional.ofNullable(this.data.remove(id));
    }

    public @NonNull Optional<T> getData(@NonNull IdKey<K> id) {
        return Optional.ofNullable(this.data.get(id));
    }

    public void removeIf(@NonNull Predicate<? super T> shouldBeRemoved) {
        final var iterator = this.data.entrySet().iterator();

        while(iterator.hasNext()) {
            final var entry = iterator.next();
            final var state = entry.getKey();
            final var value = entry.getValue();
            if (shouldBeRemoved.test(value)) {
                this.keys.remove(state);
                iterator.remove();
                dispose(value);
            }
        }
    }

    public void clear() {
        this.data.forEach((k,d) -> dispose(d));
    }

    @RequiredArgsConstructor
    public class IdBooking {

        @Getter
        private final @NonNull IdKey<K> id;
        private final @NonNull AtomicBoolean available;

        public IdBooking(@NonNull IdKey<K> id) {
            this.available = new AtomicBoolean(true);
            this.id = id;
        }

        public void free() {
            if (this.available.getAndSet(false)) {
                freeId(id);
            }
        }

        public void setData(@NonNull T value) {
            if (this.available.getAndSet(false)) {
                IdMap.this.data.put(id,value);
            } else {
                throw new IllegalArgumentException("Booking has been used");
            }
        }
    }

}
