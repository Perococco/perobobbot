package perobobbot.data.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Type;
import perobobbot.data.com.InvalidPlatformUserId;
import perobobbot.lang.Platform;
import perobobbot.lang.PlatformUser;
import perobobbot.lang.UserIdentity;
import perobobbot.persistence.PersistentObjectWithUUID;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "PLATFORM_USER", uniqueConstraints = {@UniqueConstraint(columnNames = {"PLATFORM","USER_ID"})})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "PLATFORM")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class PlatformUserEntity<I extends UserIdentity> extends PersistentObjectWithUUID {

    @Column(name = "PLATFORM", insertable = false, updatable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private @NonNull Platform platform;

    @Column(name = "USER_ID",nullable = false)
    @NotBlank
    @Getter
    private @NonNull String userId = "";

    @Getter(AccessLevel.NONE)
    @OneToOne(mappedBy = "platformUser", cascade = {CascadeType.ALL}, orphanRemoval = true, optional = true)
    private UserTokenEntity userToken;

    public @NonNull Optional<UserTokenEntity> getUserToken() {
        return Optional.ofNullable(userToken);
    }

    PlatformUserEntity(@NonNull String userId, @NonNull Platform platform) {
        super(UUID.randomUUID());
        this.userId = userId;
        this.platform = platform;
    }

    public abstract @NonNull PlatformUser toView();

    public abstract @NonNull Platform getPlatform();

    public boolean hasSamePlatformAndIdThan(@NonNull UserIdentity identity) {
        return this.getPlatform() == identity.getPlatform() && identity.getUserId().equals(this.userId);
    }

    public void checkSamePlatformAndIdThan(@NonNull UserIdentity identity) {
        if (!hasSamePlatformAndIdThan(identity)) {
            throw new InvalidPlatformUserId(identity.getUserId());
        }
    }

    void setUserToken(@NonNull UserTokenEntity userToken) {
        assert this.userToken == null:"The old token should be detached before setting a new one";
        this.userToken = userToken;
    }

    public @NonNull SafeEntity createSafe(@NonNull String channelName) {
        return new SafeEntity(this, channelName);
    }

    public @NonNull Optional<String> getTokenOwnerLogin() {
        return Optional.ofNullable(this.userToken)
                       .map(UserTokenEntity::getOwner)
                       .map(UserEntity::getLogin);
    }

    public abstract void update(@NonNull I userIdentity);

    abstract PlatformBotEntity createPlatformBot(@NonNull BotEntity bot);

    public void removeUserToken() {
        this.userToken = null;
    }
}
