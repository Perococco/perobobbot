package perobobbot.lang.token;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import perobobbot.lang.TextEncryptor;

@Value
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class EncryptedClientToken extends BaseClientToken<String> {


    public @NonNull DecryptedClientToken decrypt(@NonNull TextEncryptor textEncryptor) {
        return DecryptedClientToken.builder()
                                   .accessToken(textEncryptor.decrypt(this.getAccessToken()))
                                   .duration(getDuration())
                                   .expirationInstant(getExpirationInstant())
                                   .build();
    }
}
