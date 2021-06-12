package perobobbot.data.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.domain.base.UserTokenEntityBase;
import perobobbot.lang.TextEncryptor;
import perobobbot.lang.token.DecryptedUserTokenView;
import perobobbot.lang.token.EncryptedUserToken;
import perobobbot.lang.token.EncryptedUserTokenView;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "USER_TOKEN")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserTokenEntity extends UserTokenEntityBase {

    public UserTokenEntity(@NonNull UserEntity owner,
                           @NonNull ViewerIdentityEntity viewerIdentity,
                           @NonNull EncryptedUserToken userToken) {
        super(owner, viewerIdentity, userToken);
    }

    public @NonNull EncryptedUserToken toUserToken() {
        return EncryptedUserToken.builder()
                                 .accessToken(getAccessToken())
                                 .refreshToken(getRefreshToken())
                                 .duration(getDuration())
                                 .expirationInstant(getExpirationInstant())
                                 .scopes(getScopesAsSet())
                                 .build();
    }

    public @NonNull EncryptedUserTokenView toView() {
        return new EncryptedUserTokenView(
                getUuid(),
                getOwner().getLogin(),
                getViewerIdentity().toView(),
                toUserToken()
        );
    }

    public @NonNull DecryptedUserTokenView toDecryptedView(@NonNull TextEncryptor textEncryptor) {
        return toView().decrypt(textEncryptor);
    }
}
