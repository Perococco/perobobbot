package perobobbot.twitch.client.api;

import lombok.NonNull;

public interface TokenTypeProvider {

    @NonNull TokenType getTokenType();
}
