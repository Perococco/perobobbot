package perobobbot.twitch.client.api;

import lombok.NonNull;
import perobobbot.oauth.TokenType;

public interface TokenTypeProvider {

    @NonNull TokenType getTokenType();
}
