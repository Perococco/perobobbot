package perobobbot.data.domain;

import com.google.common.collect.ImmutableSet;
import lombok.*;
import perobobbot.lang.EncryptedTokenInfo;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
import perobobbot.lang.TextEncryptor;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.lang.token.EncryptedUserToken;
import perobobbot.lang.token.EncryptedUserTokenView;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name = "USER_TOKEN")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTokenEntity extends TokenEntityBase {

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    @Getter
    private UserEntity owner;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLATFORM_USER_ID",nullable = false)
    private PlatformUserEntity<?> platformUser;

    @Column(name = "MAIN")
    @Getter @Setter
    private boolean main;

    @Column(name = "REFRESH_TOKEN", nullable = false)
    @Getter @Setter
    private String refreshToken;

    @Column(name = "SCOPES", nullable = false,length = 2048)
    private String scopes;

    UserTokenEntity(@NonNull UserEntity owner,
                    @NonNull PlatformUserEntity<?> platformUser,
                    @NonNull EncryptedUserToken userToken) {
        super(userToken);
        this.owner = owner;
        this.platformUser = platformUser;
        this.refreshToken = userToken.getRefreshToken();
        this.scopes = Scope.scopeNamesSpaceSeparated(userToken.getScopes());
    }

    @Override
    public @NonNull Platform getPlatform() {
        return getPlatformUser().getPlatform();
    }

    public @NonNull PlatformUserEntity getPlatformUser() {
        return platformUser;
    }

    public @NonNull ImmutableSet<Scope> getScopesAsSet() {
        return Arrays.stream(scopes.split(" "))
                     .map(Scope::basic)
                     .collect(ImmutableSet.toImmutableSet());
    }

    public @NonNull EncryptedTokenInfo toTokenInfo() {
        return new EncryptedTokenInfo(getUuid(),toUserToken());
    }

    public @NonNull EncryptedUserToken toUserToken() {
        return EncryptedUserToken.builder()
                                 .accessToken(getAccessToken())
                                 .refreshToken(refreshToken)
                                 .duration(getDuration())
                                 .expirationInstant(getExpirationInstant())
                                 .scopes(getScopesAsSet())
                                 .build();
    }

    public @NonNull EncryptedUserTokenView toView() {
        return new EncryptedUserTokenView(
                getUuid(),
                owner.getLogin(),
                main,
                getPlatformUser().toView(),
                toUserToken()
        );
    }

    public @NonNull DecryptedUserTokenView toDecryptedView(@NonNull TextEncryptor textEncryptor) {
        return toView().decrypt(textEncryptor);
    }
}
