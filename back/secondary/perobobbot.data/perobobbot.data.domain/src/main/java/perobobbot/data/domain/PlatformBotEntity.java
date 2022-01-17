package perobobbot.data.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Type;
import perobobbot.lang.Platform;
import perobobbot.lang.PlatformBot;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "PLATFORM_BOT")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PLATFORM")
@NoArgsConstructor
public abstract class PlatformBotEntity extends PersistentObjectWithUUID {

    @Column(name = "PLATFORM", insertable = false, updatable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    @Getter
    private @NonNull Platform platform;

    @ManyToOne
    @JoinColumn(name = "BOT_ID",nullable = false)
    @Getter
    private @NonNull BotEntity bot;


    @OneToMany(mappedBy = "platformBot",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JoinedChannelEntity> joinedChannels = new ArrayList<>();


    PlatformBotEntity(@NonNull BotEntity bot, @NonNull Platform platform) {
        super(UUID.randomUUID());
        this.bot = bot;
        this.platform = platform;
    }

    public abstract @NonNull PlatformBot toView();
    public abstract @NonNull PlatformUserEntity<?> getPlatformUser();

    public @NonNull JoinedChannelEntity joinChannel(@NonNull String channelName) {
        return new JoinedChannelEntity(this,channelName);
    }

    void disconnectChannel(@NonNull JoinedChannelEntity joinedChannel) {
        joinedChannels.removeIf(jc -> jc.getUuid().equals(joinedChannel.getUuid()));
    }



}
