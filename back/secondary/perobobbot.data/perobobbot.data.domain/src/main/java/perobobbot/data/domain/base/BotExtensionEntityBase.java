package perobobbot.data.domain.base;

import lombok.*;
import perobobbot.data.domain.BotEntity;
import perobobbot.data.domain.ExtensionEntity;
import perobobbot.persistence.SimplePersistentObject;

import javax.persistence.*;

@MappedSuperclass
@NoArgsConstructor
@Getter @Setter
public class BotExtensionEntityBase extends SimplePersistentObject {

    @ManyToOne
    @JoinColumn(name = "BOT_ID",nullable = false)
    private BotEntity bot;

    @ManyToOne
    @JoinColumn(name = "EXTENSION_ID",nullable = false)
    private ExtensionEntity extension;

    @Setter
    @Column(name = "ENABLED",nullable = false)
    private boolean enabled;

    public BotExtensionEntityBase(@NonNull BotEntity bot, @NonNull ExtensionEntity extension) {
        this.bot = bot;
        this.extension = extension;
    }

}
