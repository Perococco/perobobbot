package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.domain.base.BotCredentialEntityBase;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "BOT_CREDENTIAL", uniqueConstraints = {
        @UniqueConstraint(columnNames = {BotCredentialEntityBase.BOT_COLUMN_NAME,BotCredentialEntityBase.CREDENTIAL_COLUMN_NAME})
})
@NoArgsConstructor
public class BotCredentialEntity extends BotCredentialEntityBase {

    public BotCredentialEntity(@NonNull BotEntity bot, @NonNull TokenEntity credentialEntity) {
        super(bot, credentialEntity);
    }
}
