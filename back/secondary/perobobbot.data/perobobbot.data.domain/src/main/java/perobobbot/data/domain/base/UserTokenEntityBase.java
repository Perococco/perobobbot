package perobobbot.data.domain.base;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import perobobbot.data.domain.UserEntity;
import perobobbot.data.domain.ViewerIdentityEntity;
import perobobbot.lang.Platform;
import perobobbot.lang.Scope;
import perobobbot.lang.token.EncryptedUserToken;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.Arrays;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
public class UserTokenEntityBase extends TokenEntityBase {

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity owner;

    @ManyToOne
    @JoinColumn(name = "VIEWER_IDENTITY_ID", nullable = false)
    private ViewerIdentityEntity viewerIdentity;

    @Column(name = "REFRESH_TOKEN", nullable = false)
    private String refreshToken;

    @Column(name = "SCOPES", nullable = false)
    private String scopes;

    public UserTokenEntityBase(@NonNull UserEntity owner,
                               @NonNull ViewerIdentityEntity viewerIdentity,
                               @NonNull EncryptedUserToken userToken) {
        super(userToken);
        this.owner = owner;
        this.viewerIdentity = viewerIdentity;
        this.refreshToken = userToken.getRefreshToken();
        this.scopes = Scope.scopeNamesSpaceSeparated(userToken.getScopes());
    }

    @Override
    public @NonNull Platform getPlatform() {
        return viewerIdentity.getPlatform();
    }

    public @NonNull ImmutableSet<Scope> getScopesAsSet() {
        return Arrays.stream(scopes.split(" "))
                     .map(Scope::basic)
                     .collect(ImmutableSet.toImmutableSet());
    }
}
