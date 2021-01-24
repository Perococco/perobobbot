package perobobbot.data.domain;

import lombok.NoArgsConstructor;
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

}
