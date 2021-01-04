package perobobbot.data.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "BOT_EXTENSION",uniqueConstraints = {@UniqueConstraint(columnNames = {"BOT_ID","EXTENSION_ID"})})
@NoArgsConstructor
@Getter
@Setter(AccessLevel.PROTECTED)
public class BotExtensionEntity extends SimplePersistentObject {

    @ManyToOne
    @JoinColumn(name = "BOT_ID",nullable = false)
    private BotEntity bot;

    @ManyToOne
    @JoinColumn(name = "EXTENSION_ID",nullable = false)
    private ExtensionEntity extension;

    @Setter
    @Column(name = "ENABLED",nullable = false)
    private boolean enabled;

    BotExtensionEntity(@NonNull BotEntity bot, @NonNull ExtensionEntity extension) {
        this.bot = bot;
        this.extension = extension;
    }

    public boolean isEnabledAndExtensionActive() {
        return this.enabled && extension.isActivated() && extension.isAvailable();
    }
}
