package perobobbot.server.config.externaluri;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.net.URI;
import java.util.concurrent.atomic.AtomicReference;

@RequiredArgsConstructor
public class CachedExternalURI implements ExternalURI {

    private final AtomicReference<URI> uri = new AtomicReference<>(null);

    private final @NonNull ExternalURI delegate;

    @Override
    public @NonNull URI getURI() {
        return uri.updateAndGet(u -> u==null?delegate.getURI():u);
    }
}
