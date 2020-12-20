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

    @NonNull UUID id = UUID.randomUUID();

    @NonNull String name;

    @Singular
    @NonNull ImmutableMap<Platform,Credentials> credentials;

    public @NonNull Optional<Credentials> findCredentials(@NonNull Platform platform) {
        return Optional.ofNullable(credentials.get(platform));
    }

    public @NonNull Optional<String> findCredentialsNick(@NonNull Platform platform) {
        return Optional.ofNullable(credentials.get(platform)).map(Credentials::getNick);
    }

    public @NonNull Credentials getCredentials(@NonNull Platform platform) {
        return findCredentials(platform).orElseThrow(() -> new NoCredentialForChatConnection(this,platform));
    }

    public @NonNull String getCredentialsNick(@NonNull Platform platform) {
        return getCredentials(platform).getNick();
    }
}
