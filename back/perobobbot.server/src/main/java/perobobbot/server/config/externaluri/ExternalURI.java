package perobobbot.server.config.externaluri;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.URIResolver;

import java.net.URI;

@RequiredArgsConstructor
public class ExternalURI implements URIResolver {

    private final @NonNull URI uri;

    private final @NonNull String context;

    public ExternalURI(@NonNull String baseUri) {
        this(URI.create(baseUri),"");
    }

    public ExternalURI(@NonNull URI uri) {
        this(uri,"");
    }

    @Override
    public @NonNull URI resolve(@NonNull String path) {
        return uri.resolve(context+prepareContext(path));
    }

    public @NonNull ExternalURI withContext(@NonNull String context) {
        return new ExternalURI(uri,this.context+prepareContext(context));
    }

    private @NonNull String prepareContext(@NonNull String context) {
        final var trimmed = context.trim();
        if (trimmed.isEmpty()) {
            return context;
        }
        return trimmed.startsWith("/")?trimmed:"/"+trimmed;

    }
}
