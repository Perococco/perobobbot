package perobobbot.rest.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Platform;
import perobobbot.lang.TypeScript;

@Value
@TypeScript
public class OAuthProcessParameter {

    @NonNull Platform platform;


}
