package perobobbot.data.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Credential;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;

import java.util.UUID;

@Value
public class DataCredentialInfo {

    public static @NonNull DataCredentialInfo with(@NonNull UUID id,
                                                   @NonNull String ownerLogin,
                                                   @NonNull Platform platform,
                                                   @NonNull String credentialLogin,
                                                   @NonNull Secret secret) {
        return new DataCredentialInfo(id, ownerLogin, platform, new Credential(credentialLogin, secret));
    }

    @NonNull UUID id;
    @NonNull String ownerLogin;
    @NonNull Platform platform;
    @NonNull Credential credential;

    public @NonNull String getNick() {
        return credential.getNick();
    }

    public boolean hasSecret() {
        return credential.getSecret().hasData();
    }
}
