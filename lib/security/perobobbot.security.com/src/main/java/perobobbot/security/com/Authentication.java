package perobobbot.security.com;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import perobobbot.lang.PasswordEncoder;
import perobobbot.lang.Platform;
import perobobbot.lang.TypeScript;

import java.util.Optional;

@Value
@TypeScript
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Authentication {

    public static @NonNull Authentication password(@NonNull String password) {
        return new Authentication(IdentificationMode.PASSWORD,password,null);
    }

    public static @NonNull Authentication openId(@NonNull Platform platform) {
        return new Authentication(IdentificationMode.OPEN_ID,null, platform);
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

    public @NonNull Authentication withPasswordEncoded(@NonNull PasswordEncoder passwordEncoder) {
        if (password == null) {
            return this;
        }
        return password(passwordEncoder.encode(password));
    }
}
