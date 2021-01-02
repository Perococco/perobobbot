package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.data.com.User;

public interface JWTokenService {

    @NonNull String createJWToken(@NonNull String login);

    @NonNull User getUserFromToken(@NonNull String token);
}
