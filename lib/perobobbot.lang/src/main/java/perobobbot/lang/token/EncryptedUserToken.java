package perobobbot.lang.token;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import perobobbot.lang.TextEncryptor;

@Value
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class EncryptedUserToken extends BaseUserToken<String> {

    public @NonNull DecryptedUserToken decrypt(@NonNull TextEncryptor textEncryptor) {
        return DecryptedUserToken.builder()
                                 .refreshToken(textEncryptor.decrypt(getRefreshToken()))
                                 .accessToken(textEncryptor.decrypt(getAccessToken()))
                                 .duration(getDuration())
                                 .expirationInstant(getExpirationInstant())
                                 .scopes(getScopes())
                                 .build();
    }
}
