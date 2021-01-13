package perobobbot.security.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;

@TypeScript
@Value
public class JwtInfo {

    @NonNull String token;

    @NonNull SimpleUser user;
}
