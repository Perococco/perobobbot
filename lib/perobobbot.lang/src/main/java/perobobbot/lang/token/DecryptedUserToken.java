package perobobbot.lang.token;

import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.SuperBuilder;
import perobobbot.lang.Scope;
import perobobbot.lang.Secret;
import perobobbot.lang.TextEncryptor;

import java.time.Instant;

@Value
@EqualsAndHashCode(callSuper = true)
public class DecryptedUserToken extends BaseUserToken<Secret> {

    @Builder
    public DecryptedUserToken(@NonNull Secret accessToken, @NonNull Secret refreshToken, @NonNull Instant expirationInstant, long duration, @NonNull ImmutableSet<? extends Scope> scopes) {
        super(accessToken, refreshToken, expirationInstant, duration, scopes);
    }

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
