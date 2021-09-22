package perobobbot.rest.com;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
import perobobbot.lang.TypeScript;

@Value
@TypeScript
public class OAuthProcessParameter {

    @NonNull Platform platform;


}
