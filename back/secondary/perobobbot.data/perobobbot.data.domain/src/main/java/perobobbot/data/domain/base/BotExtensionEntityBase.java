package perobobbot.data.domain.base;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.data.domain.BotEntity;
import perobobbot.data.domain.BotExtensionEntity;
import perobobbot.data.domain.ExtensionEntity;
import perobobbot.persistence.SimplePersistentObject;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

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
    private boolean enabled = true;

    public BotExtensionEntityBase(@NonNull BotEntity bot, @NonNull ExtensionEntity extension) {
        this.bot = bot;
        this.extension = extension;
    }

}
