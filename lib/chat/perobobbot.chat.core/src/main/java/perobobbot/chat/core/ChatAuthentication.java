package perobobbot.chat.core;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Secret;

@Value
public class ChatAuthentication {

    @NonNull String nick;
    @NonNull Secret pass;

}
