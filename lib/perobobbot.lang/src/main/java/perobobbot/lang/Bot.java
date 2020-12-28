package perobobbot.lang;

import com.google.common.collect.ImmutableMap;
import lombok.*;

import java.util.Optional;
import java.util.UUID;

@Value
@EqualsAndHashCode(of = "id")
@Builder
@ToString(of = {"name","id"})
public class Bot {

    /**
     * A uniq identifier used to id the bot through the application
     */
    @NonNull UUID id = UUID.randomUUID();

    /**
     * The name of the bot
     */
    @NonNull String name;

    /**
     * The credentials to use for each platform
     */
    @Singular
    @NonNull ImmutableMap<Platform,Credentials> credentials;

    public @NonNull Optional<Credentials> findCredentials(@NonNull Platform platform) {
        return Optional.ofNullable(credentials.get(platform));
    }

    public @NonNull Credentials getCredentials(@NonNull Platform platform) {
        return findCredentials(platform).orElseThrow(() -> new NoCredentialForPlatformConnection(this, platform));
    }

    public @NonNull String getCredentialsNick(@NonNull Platform platform) {
        return getCredentials(platform).getNick();
    }
}
