package perobobbot.data.security.permission;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import perobobbot.data.security.DataPermission;
import perobobbot.data.service.OAuthService;
import perobobbot.data.service.UnsecuredService;

import java.io.Serializable;
import java.util.UUID;

@Component
public class UserTokenEntityPermissions extends DataEntityPermission {

    private final OAuthService oAuthService;

    public UserTokenEntityPermissions(@NonNull @UnsecuredService OAuthService oAuthService) {
        super("UserTokenEntity");
        this.oAuthService = oAuthService;
    }

    @Override
    protected boolean hasPermissionWithObject(@NonNull UserDetails principal, Object targetDomainObject, @NonNull DataPermission permission) {
        return false;
    }

    @Override
    protected boolean hasPermissionWithId(@NonNull UserDetails principal, Serializable tokenId, @NonNull DataPermission permission) {
        return switch (permission) {
            case DELETE,READ -> doesNotExistOrIsMyToken(principal, (UUID)tokenId);
            default -> false;
        };
    }

    private boolean doesNotExistOrIsMyToken(UserDetails userDetails, UUID tokenId) {
        final var token = oAuthService.findUserToken(tokenId).orElse(null);
        if (token == null) {
            return true;
        }
        return token.getOwnerLogin().equals(userDetails.getUsername());
    }
}
