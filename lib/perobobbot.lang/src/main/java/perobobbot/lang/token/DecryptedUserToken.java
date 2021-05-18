package perobobbot.lang.token;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.NonFinal;
import lombok.experimental.SuperBuilder;
import perobobbot.lang.Secret;
import perobobbot.lang.TextEncryptor;

@Value
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class DecryptedUserToken extends BaseUserToken<Secret> {

    public @NonNull EncryptedUserToken encrypt(@NonNull TextEncryptor textEncryptor) {
        return EncryptedUserToken.builder()
                                 .accessToken(textEncryptor.encrypt(getAccessToken()))
                                 .refreshToken(textEncryptor.encrypt(getRefreshToken()))
                                 .duration(getDuration())
                                 .expirationInstant(getExpirationInstant())
                                 .scopes(getScopes())
                                 .build();
    }

}
