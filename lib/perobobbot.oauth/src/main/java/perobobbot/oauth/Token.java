package perobobbot.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.*;
import perobobbot.lang.Scope;
import perobobbot.lang.Secret;
import perobobbot.lang.token.DecryptedUserToken;

import java.time.Instant;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
public class Token {

    @NonNull Secret accessToken;
    @Getter(AccessLevel.NONE)
    Secret refreshToken;
    long duration;
    @NonNull Instant expirationInstant;
    @NonNull ImmutableSet<? extends Scope> scopes;
    @NonNull String tokenType;

    public @NonNull Optional<Secret> getRefreshToken() {
        return Optional.ofNullable(refreshToken);
    }

    public @NonNull DecryptedUserToken toDecryptedUserToken() {
        return DecryptedUserToken.builder()
                                 .accessToken(this.getAccessToken())
                                 .duration(this.getDuration())
                                 .expirationInstant(this.getExpirationInstant())
                                 .refreshToken(this.getRefreshToken().orElse(Secret.empty()))
                                 .scopes(this.getScopes())
                                 .build();
    }
}
