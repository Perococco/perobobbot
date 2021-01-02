package perobobbot.data.domain;

import com.google.common.collect.ImmutableMap;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import perobobbot.lang.Bot;
import perobobbot.lang.Credential;
import perobobbot.lang.Platform;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "BOT")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Setter(AccessLevel.PROTECTED)
@Getter
public class BotEntity extends PersistentObjectWithUUID{

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false,foreignKey = @ForeignKey(name = "FK_BOT__USER"))
    private UserEntity owner;

    @Column(name = "NAME",nullable = false)
    @NotBlank
    @Setter
    private String name;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "bot")
    @Fetch(FetchMode.JOIN)
    private List<BotCredentialEntity> credentials = new ArrayList<>();

    public BotEntity(UserEntity owner, @NonNull String name) {
        super(UUID.randomUUID());
        this.owner = owner;
        this.name = name;
    }

    public @NonNull String getOwnerLogin() {
        return owner.getLogin();
    }

    public @NonNull ImmutableMap<Platform, Credential> getCredentialsAsMap() {
        return credentials.stream().collect(ImmutableMap.toImmutableMap(BotCredentialEntity::getPlatform, BotCredentialEntity::getCredential));
    }

    public @NonNull Bot toView() {
        return Bot.builder()
                  .id(uuid)
                  .name(name)
                  .credentials(getCredentialsAsMap())
                  .build();
    }
}
