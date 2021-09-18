package perobobbot.server.config.externaluri;

import lombok.NonNull;

public record ConstExternalURIProvider(ExternalURI value) implements ExternalURIProvider {

    @Override
    public @NonNull ExternalURI get() {
        return this.value;
    }
}
