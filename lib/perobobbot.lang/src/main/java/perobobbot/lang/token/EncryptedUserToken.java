package perobobbot.lang.token;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Scope;
import perobobbot.lang.TextEncryptor;

import java.time.Instant;

@Value
@EqualsAndHashCode(callSuper = true)
public class EncryptedUserToken extends BaseUserToken<String> {

    @Builder
    public EncryptedUserToken(@NonNull String accessToken, @NonNull String refreshToken, @NonNull Instant expirationInstant, long duration, @NonNull ImmutableSet<? extends Scope> scopes) {
        super(accessToken, refreshToken, expirationInstant, duration, scopes);
    }

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
