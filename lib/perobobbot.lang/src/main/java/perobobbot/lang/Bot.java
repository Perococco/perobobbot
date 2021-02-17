package perobobbot.lang;

import com.google.common.collect.ImmutableMap;
import lombok.*;

import java.util.Optional;
import java.util.UUID;

@TypeScript
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
    @Getter
    private final @NonNull ImmutableMap<Platform,Credential> credentials;

    public @NonNull Optional<Credential> findCredential(@NonNull Platform platform) {
        return Optional.ofNullable(credentials.get(platform));
    }

    public @NonNull Credential getCredential(@NonNull Platform platform) {
        return findCredential(platform).orElseThrow(() -> new NoCredentialForPlatformConnection(this, platform));
    }

    public @NonNull String getCredentialNick(@NonNull Platform platform) {
        return getCredential(platform).getNick();
    }

    public @NonNull ChatConnectionInfo extractConnectionInfo(@NonNull Platform platform) {
        return new ChatConnectionInfo(getId(), getName(), platform, getCredential(platform));
    }
}
