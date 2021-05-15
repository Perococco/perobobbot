package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.annotations.Type;
import perobobbot.data.com.DataCredentialInfo;
import perobobbot.data.domain.UserEntity;
import perobobbot.data.domain.converter.SecretConverter;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@MappedSuperclass
@NoArgsConstructor
@Getter @Setter
public class TokenEntityBase extends PersistentObjectWithUUID {

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity owner;

    @Column(name = "PLATFORM")
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private Platform platform;

    @Column(name = "NICK", nullable = false)
    private String nick = "";

    @Column(name = "SECRET", nullable = false)
    @Convert(converter = SecretConverter.class)
    private Secret secret = Secret.empty();

    public TokenEntityBase(@NotBlank UserEntity owner, @NonNull Platform platform) {
        super(UUID.randomUUID());
        this.owner = owner;
        this.platform = platform;
        this.nick = "";
        this.secret = new Secret("");
    }

    public @NonNull DataCredentialInfo toView() {
        return DataCredentialInfo.with(this.uuid, this.owner.getLogin(), this.platform, this.nick, this.secret);
    }
}