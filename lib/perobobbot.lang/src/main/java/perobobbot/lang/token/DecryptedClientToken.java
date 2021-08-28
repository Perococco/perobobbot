package perobobbot.lang.token;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import perobobbot.lang.Secret;
import perobobbot.lang.TextEncryptor;

import java.time.Instant;

@Value
@EqualsAndHashCode(callSuper = true)
public class DecryptedClientToken extends BaseClientToken<Secret> {

    @Builder
    public DecryptedClientToken(@NonNull Secret accessToken, @NonNull Instant expirationInstant, long duration) {
        super(accessToken, expirationInstant, duration);
    }

    public @NonNull EncryptedClientToken encrypt(@NonNull TextEncryptor textEncryptor) {
        return EncryptedClientToken.builder()
                                   .accessToken(textEncryptor.encrypt(getAccessToken()))
                                   .duration(getDuration())
                                   .expirationInstant(getExpirationInstant())
                                   .build();
    }
}
