package perobobbot.lang.token;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import perobobbot.lang.Secret;
import perobobbot.lang.TextEncryptor;

@Value
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class DecryptedClientToken extends BaseClientToken<Secret> {


    public @NonNull EncryptedClientToken encrypt(@NonNull TextEncryptor textEncryptor) {
        return EncryptedClientToken.builder()
                                   .accessToken(textEncryptor.encrypt(getAccessToken()))
                                   .duration(getDuration())
                                   .expirationInstant(getExpirationInstant())
                                   .build();
    }
}
