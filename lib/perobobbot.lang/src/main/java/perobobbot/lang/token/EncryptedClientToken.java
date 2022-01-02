package perobobbot.lang.token;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TextEncryptor;

import java.time.Instant;

@Value
@EqualsAndHashCode(callSuper = true)
public class EncryptedClientToken extends BaseClientToken<String> {

    @Builder
    public EncryptedClientToken(@NonNull String accessToken, @NonNull Instant expirationInstant, long duration) {
        super(accessToken, expirationInstant, duration);
    }

    public @NonNull DecryptedClientToken decrypt(@NonNull TextEncryptor textEncryptor) {
        return DecryptedClientToken.builder()
                                   .accessToken(textEncryptor.decrypt(this.getAccessToken()))
                                   .duration(getDuration())
                                   .expirationInstant(getExpirationInstant())
                                   .build();
    }
}
