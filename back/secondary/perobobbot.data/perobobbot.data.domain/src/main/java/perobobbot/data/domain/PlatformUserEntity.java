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
import perobobbot.lang.token.EncryptedUserToken;
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

    @Column(name = "USER_ID",nullable = false)
    @NotBlank
    @Getter
    private @NonNull String userId = "";

    @Getter(AccessLevel.NONE)
    @OneToOne(mappedBy = "platformUser", cascade = {CascadeType.ALL}, orphanRemoval = true, optional = true)
    private UserTokenEntity userToken;

    @Column(name = "PLATFORM", insertable = false, updatable = false)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    @Getter
    private Platform platform;


    public @NonNull Optional<UserTokenEntity> getUserToken() {
        return Optional.ofNullable(userToken);
    }

    PlatformUserEntity(@NonNull String userId, @NonNull Platform platform) {
        super(UUID.randomUUID());
        this.userId = userId;
        this.platform = platform;
    }

    public abstract @NonNull PlatformUser toView();

    public boolean hasSamePlatformAndIdThan(@NonNull UserIdentity identity) {
        return this.platform == identity.getPlatform() && identity.getUserId().equals(this.userId);
    }

    public void checkSamePlatformAndIdThan(@NonNull UserIdentity identity) {
        if (!hasSamePlatformAndIdThan(identity)) {
            throw new InvalidPlatformUserId(identity.getUserId());
        }
    }

    @NonNull UserTokenEntity setUserToken(@NonNull UserEntity userEntity, @NonNull EncryptedUserToken userToken) {
        this.userToken = new UserTokenEntity(userEntity, this, userToken);
        return this.userToken;
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
}
