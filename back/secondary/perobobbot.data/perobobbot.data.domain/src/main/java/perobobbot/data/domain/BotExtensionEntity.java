package perobobbot.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.data.com.BotExtension;
import perobobbot.persistence.SimplePersistentObject;

import javax.persistence.*;

@Entity
@Table(name = "BOT_EXTENSION",uniqueConstraints = {@UniqueConstraint(columnNames = {"BOT_ID","EXTENSION_ID"})})
@NoArgsConstructor
public class BotExtensionEntity extends SimplePersistentObject {

    @ManyToOne
    @JoinColumn(name = "BOT_ID",nullable = false)
    private BotEntity bot;

    @ManyToOne
    @JoinColumn(name = "EXTENSION_ID",nullable = false)
    @Getter
    private ExtensionEntity extension;

    @Column(name = "ENABLED",nullable = false)
    @Getter @Setter
    private boolean enabled = true;

    public BotExtensionEntity(@NonNull BotEntity bot, @NonNull ExtensionEntity extension) {
        this.bot = bot;
        this.extension = extension;
    }

    public boolean isEnabledAndExtensionActiveAndAvailable() {
        return this.enabled && extension.isActiveAndAvailable();
    }

    public @NonNull BotExtension toView() {
        return new BotExtension(this.bot.toView(), this.extension.toView(), this.enabled);
    }

}
