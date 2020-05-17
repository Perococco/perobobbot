package perobobbot.data.domain;

import lombok.NonNull;

public interface JwtTokenGenerator {

    @NonNull
    String createTokenFromUser(@NonNull User user);
}
