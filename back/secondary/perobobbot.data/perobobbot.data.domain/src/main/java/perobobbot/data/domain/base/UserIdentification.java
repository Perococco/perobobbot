package perobobbot.data.domain.base;

import lombok.*;
import org.hibernate.annotations.Type;
import perobobbot.lang.Platform;
import perobobbot.security.com.Identification;
import perobobbot.security.com.IdentificationMode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Optional;

@Embeddable
@NoArgsConstructor
@Getter @Setter(AccessLevel.PROTECTED)
public class UserIdentification {

    @Column(name = "IDENTIFICATION_MODE",nullable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private IdentificationMode mode;

    @Column(name = "PASSWORD",nullable = true)
    private String password;

    @Column(name = "OPENID_PLATFORM",nullable = true)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private Platform openIdPlatform;

    public UserIdentification(@NonNull Identification identification) {
        this.mode = identification.getMode();
        this.password = identification.getPassword().orElse(null);
        this.openIdPlatform = identification.getOpenIdPlatform().orElse(null);
    }

    public @NonNull Optional<String> getPassword() {
        return Optional.ofNullable(password);
    }

    public @NonNull Optional<Platform> getOpenIdPlatform() {
        return Optional.ofNullable(openIdPlatform);
    }

    public Identification toView() {
        if (password == null) {
            return Identification.openId(openIdPlatform);
        }
        return Identification.password(password);
    }
}
