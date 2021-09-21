package perobobbot.security.com;

import lombok.NonNull;
import perobobbot.oauth.Token;

public record OAuthToken(@NonNull Token token, @NonNull JwtInfo jwtInfo) {


}
