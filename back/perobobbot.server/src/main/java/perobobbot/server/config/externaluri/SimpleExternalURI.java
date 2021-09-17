package perobobbot.server.config.externaluri;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.net.URI;

@RequiredArgsConstructor
public class SimpleExternalURI implements ExternalURI {

    private final @NonNull URI uri;

    @Override
    public @NonNull URI getURI() {
        return uri;
    }

    public @NonNull SimpleExternalURI resolve(@NonNull String path) {
        final var trimmedPath = path.trim();
        if (trimmedPath.isEmpty() || trimmedPath.equals("/")) {
            return this;
        }
        final var corrected = trimmedPath.startsWith("/") ? trimmedPath : "/" + trimmedPath;
        return new SimpleExternalURI(this.uri.resolve(corrected));
    }

}
