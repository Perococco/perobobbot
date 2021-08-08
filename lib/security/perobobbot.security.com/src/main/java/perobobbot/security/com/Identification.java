package perobobbot.security.com;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import perobobbot.lang.PasswordEncoder;
import perobobbot.lang.Platform;

import java.util.Optional;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Identification {

    public static @NonNull Identification password(@NonNull String password) {
        return new Identification(IdentificationMode.PASSWORD,password,null);
    }

    public static @NonNull Identification openId(@NonNull Platform platform) {
        return new Identification(IdentificationMode.OPEN_ID,null, platform);
    }

    @NonNull IdentificationMode mode;

    String password;

    Platform openIdPlatform;

    public @NonNull Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    public @NonNull Optional<Platform> getOpenIdPlatform() {
        return Optional.ofNullable(openIdPlatform);
    }

    public @NonNull Identification withPasswordEncoded(@NonNull PasswordEncoder passwordEncoder) {
        if (password == null) {
            return this;
        }
        return password(passwordEncoder.encode(password));
    }
}
