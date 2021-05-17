package perobobbot.oauth;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Platform;
import perobobbot.oauth._private.OAuthObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

@Value
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ClientProperties {

    @Getter(AccessLevel.NONE)
    @NonNull ImmutableMap<Platform,ClientProperty> clientProperties;

    public static @NonNull ClientProperties fromFile(Path clientPropertyFile) {
        try {
            return new OAuthObjectMapper().readerFor(ClientProperties.class).readValue(clientPropertyFile.toFile());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public @NonNull ClientProperty getClientProperty(@NonNull Platform platform) {
        final var property = clientProperties.get(platform);
        if (property == null) {
            throw new IllegalStateException("No ClientProperty defined for platform '"+platform+"'");
        }
        return property;
    }
}
