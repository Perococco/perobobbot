package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import perobobbot.data.domain.BotEntity;
import perobobbot.data.domain.CredentialEntity;
import perobobbot.persistence.SimplePersistentObject;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor
@Getter @Setter
public class BotCredentialEntityBase extends SimplePersistentObject {

    public static final String BOT_COLUMN_NAME = "BOT_ID";
    public static final String CREDENTIAL_COLUMN_NAME = "CREDENTIAL_ID";

    @ManyToOne
    @JoinColumn(name = BOT_COLUMN_NAME,nullable = false)
    private BotEntity bot;

    @ManyToOne
    @JoinColumn(name = CREDENTIAL_COLUMN_NAME,nullable = false)
    private CredentialEntity credentialEntity;

}
