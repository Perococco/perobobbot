package perobobbot.data.domain;

import com.google.common.collect.ImmutableMap;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import perobobbot.data.com.BotHasThisExtensionAlready;
import perobobbot.lang.Bot;
import perobobbot.lang.Credential;
import perobobbot.lang.MapTool;
import perobobbot.lang.Platform;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "BOT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@Getter
public class BotEntity extends PersistentObjectWithUUID{

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity owner;

    @Column(name = "NAME",nullable = false)
    @NotBlank
    @Setter
    private String name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "bot")
    @Fetch(FetchMode.JOIN)
    @MapKey(name = "platform")
    private Map<Platform,BotCredentialEntity> credentials = new HashMap<>();

    @OneToMany(mappedBy = "bot",cascade = CascadeType.ALL)
    @MapKey(name = "extension")
    private Map<ExtensionEntity,BotExtensionEntity> extensions = new HashMap<>();

    public BotEntity(UserEntity owner, @NonNull String name) {
        super(UUID.randomUUID());
        this.owner = owner;
        this.name = name;
    }

    public @NonNull String getOwnerLogin() {
        return owner.getLogin();
    }

    public @NonNull ImmutableMap<Platform, Credential> getCredentialsAsMap() {
        return MapTool.unsafeMapValues(credentials, BotCredentialEntity::getCredential);
    }

    public @NonNull BotExtensionEntity addExtension(@NonNull ExtensionEntity extension) {
        if (extensions.containsKey(extension)) {
            throw new BotHasThisExtensionAlready(this.uuid,extension.uuid);
        }
        final var botExtension = new BotExtensionEntity(this,extension);
        this.extensions.put(extension,botExtension);
        return botExtension;
    }

    public @NonNull Bot toView() {
        return Bot.builder()
                  .id(uuid)
                  .ownerLogin(getOwnerLogin())
                  .name(name)
                  .credentials(getCredentialsAsMap())
                  .build();
    }
}
