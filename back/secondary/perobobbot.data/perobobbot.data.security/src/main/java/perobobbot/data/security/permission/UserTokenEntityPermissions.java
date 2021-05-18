package perobobbot.data.security.permission;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import perobobbot.data.security.DataPermission;
import perobobbot.data.service.TokenService;
import perobobbot.data.service.UnsecuredService;

import java.io.Serializable;
import java.util.UUID;

@Component
public class UserTokenEntityPermissions extends DataEntityPermission {


    private final @NonNull TokenService tokenService;

    public UserTokenEntityPermissions(@NonNull @UnsecuredService TokenService tokenService) {
        super("UserTokenEntity");
        this.tokenService = tokenService;
    }

    @Override
    protected boolean hasPermissionWithObject(@NonNull UserDetails principal, Object targetDomainObject, @NonNull DataPermission permission) {
        return false;
    }

    @Override
    protected boolean hasPermissionWithId(@NonNull UserDetails principal, Serializable parameter, @NonNull DataPermission permission) {
        return switch (permission) {
            case DELETE_TOKEN, READ_TOKEN -> doesNotExistOrIsMyCredential(principal, (UUID)parameter);
            case CREATE,READ -> isLoginOfAuthenticatedUser(principal, (String)parameter);
            default -> false;
        };
    }

    private boolean doesNotExistOrIsMyCredential(@NonNull UserDetails userDetails, @NonNull UUID tokenId) {
        final var tokenView = tokenService.findUserToken(tokenId).orElse(null);
        if (tokenView == null) {
            return true;
        }
        return tokenView.getOwnerLogin().equals(userDetails.getUsername());
    }

    private boolean isLoginOfAuthenticatedUser(@NonNull UserDetails userDetails, @NonNull String ownerLogin) {
        return userDetails.getUsername().equals(ownerLogin);
    }
}
