package perobobbot.lang;

import lombok.NonNull;
import lombok.Value;

@Value
public class Credential {

    @NonNull String nick;
    @NonNull Secret pass;

}
