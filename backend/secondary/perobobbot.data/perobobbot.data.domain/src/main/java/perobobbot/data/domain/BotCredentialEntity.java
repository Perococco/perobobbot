package perobobbot.data.domain;

import lombok.*;
import org.hibernate.annotations.Type;
import perobobbot.lang.Credential;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;
import perobobbot.persistence.SimplePersistentObject;

import javax.persistence.*;

@Entity
@Table(name = "BOT_CREDENTIAL", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"BOT_ID","PLATFORM"})
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PROTECTED)
public class BotCredentialEntity extends SimplePersistentObject {

    @ManyToOne
    @JoinColumn(name = "BOT_ID",nullable = false)
    private BotEntity bot;

    @Column(name = "PLATFORM",nullable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private Platform platform;

    @Column(name = "NICK",nullable = false)
    private String nick;

    @Column(name = "PASS",nullable = false)
    private String pass;

    public @NonNull Credential getCredential() {
        return new Credential(nick, new Secret(pass));
    }
}
