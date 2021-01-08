package perobobbot.data.domain;

import lombok.*;
import org.hibernate.annotations.Type;
import perobobbot.data.com.DataCredentialInfo;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;
import perobobbot.lang.StringTools;
import perobobbot.persistence.PersistentObjectWithUUID;
import perobobbot.data.domain.converter.SecretConvert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Entity
@Table(name = "CREDENTIAL")
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PROTECTED)
public class CredentialEntity extends PersistentObjectWithUUID {

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity owner;

    @Column(name = "PLATFORM")
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private Platform platform;

    @Column(name = "NICK", nullable = false)
    private String nick = "";

    @Column(name = "SECRET", nullable = false)
    @Convert(converter = SecretConvert.class)
    @Setter
    private Secret secret = Secret.empty();

    public CredentialEntity(@NotBlank UserEntity owner, @NonNull Platform platform, @NonNull String nick) {
        this.owner = owner;
        this.platform = platform;
        this.nick = nick;
        this.secret = new Secret("");
    }

    public @NonNull Optional<Secret> getSecret() {
        return Optional.ofNullable(secret).filter(s -> StringTools.hasData(s.getValue()));
    }

    public @NonNull DataCredentialInfo toView() {
        return DataCredentialInfo.with(this.uuid, this.owner.getLogin(), this.platform, this.nick, this.secret);
    }
}
