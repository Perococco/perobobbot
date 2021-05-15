package perobobbot.oauth;

import com.google.common.collect.ImmutableSet;
import lombok.*;
import perobobbot.lang.Scope;

import java.time.Instant;
import java.util.Optional;

@Value
@Builder(toBuilder = true)
public class Token {

   @NonNull String accessToken;
   @Getter(AccessLevel.NONE)
   String refreshToken;
   long duration;
   @NonNull Instant expirationInstant;
   @NonNull ImmutableSet<? extends Scope> scopes;
   @NonNull String tokenType;

   public @NonNull Optional<String> getRefreshToken() {
      return Optional.ofNullable(refreshToken);
   }
}
