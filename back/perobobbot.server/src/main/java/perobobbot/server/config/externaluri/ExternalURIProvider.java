package perobobbot.server.config.externaluri;

import lombok.NonNull;

public interface ExternalURIProvider {

    @NonNull ExternalURI get();
}
