package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Secret;

@Value
public class Credentials {

    @NonNull String nick;
    @NonNull Secret pass;

}
