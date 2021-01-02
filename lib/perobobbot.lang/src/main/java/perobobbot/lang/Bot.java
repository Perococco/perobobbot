package perobobbot.lang;

import com.google.common.collect.ImmutableMap;
import lombok.*;

import java.util.Optional;
import java.util.UUID;

@Builder
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class Bot {

    /**
     * A uniq identifier used to id the bot through the application
     */
    @Getter
    private final @NonNull UUID id;

    @Getter
    private final @NonNull String ownerLogin;

    /**
     * The name of the bot
     */
    @Getter
    private final @NonNull String name;

    @Singular
    private final @NonNull ImmutableMap<Platform,Credential> credentials;

    public @NonNull Optional<Credential> findCredentials(@NonNull Platform platform) {
        return Optional.ofNullable(credentials.get(platform));
    }

    public @NonNull Credential getCredentials(@NonNull Platform platform) {
        return findCredentials(platform).orElseThrow(() -> new NoCredentialForPlatformConnection(this, platform));
    }

    public @NonNull String getCredentialsNick(@NonNull Platform platform) {
        return getCredentials(platform).getNick();
    }
}
