package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.com.BotExtension;
import perobobbot.data.domain.base.BotExtensionEntityBase;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

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

    public @NonNull BotExtension toView() {
        return new BotExtension(this.getBot().toView(), this.getExtension().toView(), this.isEnabled());
    }


}
