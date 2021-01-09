package perobobbot.data.domain;

import lombok.*;
import perobobbot.data.domain.base.BotExtensionEntityBase;
import perobobbot.persistence.SimplePersistentObject;

import javax.persistence.*;

@Entity
@Table(name = "BOT_EXTENSION",uniqueConstraints = {@UniqueConstraint(columnNames = {"BOT_ID","EXTENSION_ID"})})
@NoArgsConstructor
public class BotExtensionEntity extends BotExtensionEntityBase {

    BotExtensionEntity(@NonNull BotEntity bot, @NonNull ExtensionEntity extension) {
        super(bot,extension);
    }

    public boolean isEnabledAndExtensionActiveAndAvailable() {
        return this.isEnabled() && getExtension().isActiveAndAvailable();
    }

}
