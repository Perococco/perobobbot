package perobobbot.data.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import perobobbot.persistence.SimplePersistentObject;

import javax.persistence.*;

@Entity
@Table(name = "BOT_CREDENTIAL", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"BOT_ID","CREDENTIAL_ID"})
})
@NoArgsConstructor
@Getter @Setter(AccessLevel.PROTECTED)
public class BotCredentialEntity extends SimplePersistentObject {

    public static final String BOT_COLUMN_NAME = "BOT_ID";
    public static final String CREDENTIAL_COLUMN_NAME = "CREDENTIAL_ID";

    @ManyToOne
    @JoinColumn(name = "BOT_ID",nullable = false)
    private BotEntity bot;

    @ManyToOne
    @JoinColumn(name = "CREDENTIAL_ID",nullable = false)
    private UserTokenEntity credentialEntity;

    public BotCredentialEntity(BotEntity bot, UserTokenEntity credentialEntity) {
        this.bot = bot;
        this.credentialEntity = credentialEntity;
    }

}
