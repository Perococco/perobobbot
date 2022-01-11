package perobobbot.data.domain;

import lombok.*;
import org.hibernate.annotations.Type;
import perobobbot.data.com.InvalidIdentificationMode;
import perobobbot.lang.Platform;
import perobobbot.security.com.Authentication;
import perobobbot.security.com.IdentificationMode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Optional;

@Embeddable
@NoArgsConstructor
@Getter @Setter(AccessLevel.PROTECTED)
public class UserAuthentication {

    @Column(name = "IDENTIFICATION_MODE",nullable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private IdentificationMode mode;

    @Column(name = "PASSWORD",nullable = true)
    private String password;

    @Column(name = "OPENID_PLATFORM",nullable = true)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private Platform openIdPlatform;

    public UserAuthentication(@NonNull Authentication authentication) {
        this.mode = authentication.getMode();
        this.password = authentication.getPassword().orElse(null);
        this.openIdPlatform = authentication.getOpenIdPlatform().orElse(null);
    }

    public @NonNull Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    public @NonNull Optional<Platform> getOpenIdPlatform() {
        return Optional.ofNullable(openIdPlatform);
    }

    public Authentication toView() {
        if (password == null) {
            return Authentication.openId(openIdPlatform);
        }
        return Authentication.password(password);
    }

    public void changePassword(@NonNull String encodedPassword) {
        if (mode != IdentificationMode.PASSWORD) {
            throw new InvalidIdentificationMode(IdentificationMode.PASSWORD, mode);
        }

        this.password = encodedPassword;
    }
}
