package perobobbot.data.domain;

import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.data.com.BotHasThisExtensionAlready;
import perobobbot.lang.Bot;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Entity
@Table(name = "BOT",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"USER_ID", "NAME"})
})
@NoArgsConstructor
public class BotEntity extends PersistentObjectWithUUID {

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity owner;

    @Column(name = "NAME",nullable = false)
    @NotBlank
    @Setter
    private String name;

    @OneToMany(mappedBy = "bot", targetEntity = PlatformBotEntity.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlatformBotEntity> platformBots = new ArrayList<>();

    @OneToMany(mappedBy = "bot",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<BotExtensionEntity> botExtensions = new ArrayList<>();


    public BotEntity(UserEntity owner, @NonNull String name) {
        super(UUID.randomUUID());
        this.owner = owner;
        this.name = name;
    }



    public @NonNull Stream<PlatformBotEntity> platformBotStream() {
        return platformBots.stream();
    }

    public @NonNull String getOwnerLogin() {
        return owner.getLogin();
    }


    public @NonNull PlatformBotEntity addPlatformBot(@NonNull PlatformUserEntity<?> platformUser) {
        final var platformBot = platformUser.createPlatformBot(this);
        this.platformBots.add(platformBot);
        return platformBot;
    }

    public @NonNull BotExtensionEntity addExtension(@NonNull ExtensionEntity extension) {
        if (hasExtension(extension)) {
            throw new BotHasThisExtensionAlready(this.uuid,extension.getUuid());
        }
        final var botExtension = new BotExtensionEntity(this,extension);
        this.botExtensions.add(botExtension);
        return botExtension;
    }

    /**
     * @param extensionName the name of the extension to find
     * @return the found extension in an optional if found, en empty optional otherwise
     */
    public @NonNull Optional<BotExtensionEntity> findBotExtension(@NonNull String extensionName) {
        return botExtensions.stream()
                                 .filter(e -> e.getExtension().hasName(extensionName))
                                 .findAny();
    }

    public boolean hasExtension(@NonNull ExtensionEntity extension) {
        return findBotExtension(extension.getName()).isPresent();
    }

    public @NonNull Bot toView() {
        return Bot.builder()
                  .id(uuid)
                  .ownerLogin(getOwnerLogin())
                  .name(name)
                  .build();
    }



}
