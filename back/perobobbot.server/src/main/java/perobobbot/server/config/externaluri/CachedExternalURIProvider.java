package perobobbot.server.config.externaluri;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class CachedExternalURIProvider implements ExternalURIProvider {

    private final AtomicReference<ExternalURI> cache = new AtomicReference<>(null);

    private final @NonNull ExternalURIProvider delegate;

    @Override
    public @NonNull ExternalURI get() {
        return cache.updateAndGet(cache -> cache == null?delegate.get():cache);
    }

}
