package perobobbot.data.security.permission;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import perobobbot.data.security.DataPermission;
import perobobbot.data.service.CredentialService;
import perobobbot.data.service.UnsecuredService;
import perobobbot.data.service.UserService;

import java.io.Serializable;
import java.util.UUID;

@Component
public class CredentialEntityPermissions extends DataEntityPermission {

    private final CredentialService credentialService;

    public CredentialEntityPermissions(@NonNull @UnsecuredService CredentialService credentialService) {
        super("CredentialEntity");
        this.credentialService = credentialService;
    }

    @Override
    protected boolean hasPermissionWithObject(@NonNull UserDetails principal, Object targetDomainObject, @NonNull DataPermission permission) {
        return false;
    }

    @Override
    protected boolean hasPermissionWithId(@NonNull UserDetails principal, Serializable targetId, @NonNull DataPermission permission) {
        return switch (permission) {
            case DELETE,READ,UPDATE -> doesNotExistOrIsMyCredential(principal, (UUID)targetId);
            default -> false;
        };
    }

    private boolean doesNotExistOrIsMyCredential(@NonNull UserDetails userDetails, @NonNull UUID uuid) {
        final var credentialInfo = credentialService.findCredential(uuid).orElse(null);
        if (credentialInfo == null) {
            return true;
        }
        return credentialInfo.getOwnerLogin().equals(userDetails.getUsername());
    }
}
