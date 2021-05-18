package perobobbot.twitch.client.api;

import lombok.NonNull;
import perobobbot.lang.TokenType;

public interface TokenTypeProvider {

    @NonNull TokenType getTokenType();
}
