package perobobbot.security;

import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

public abstract class UserDetailTargetedPermissionEvaluator extends TargetedPermissionEvaluatorBase {

    public UserDetailTargetedPermissionEvaluator(@NonNull Class<?> targetType) {
        super(targetType);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (!authentication.isAuthenticated()) {
            return false;
        }
        final var principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return hasPermission((UserDetails) principal,targetDomainObject,permission);
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if (!authentication.isAuthenticated()) {
            return false;
        }
        final var principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            return hasPermission((UserDetails) principal,targetId,targetType,permission);
        }
        return false;
    }

    protected abstract boolean hasPermission(@NonNull UserDetails userDetails, Object targetDomainObject, Object permission);

    protected abstract boolean hasPermission(@NonNull UserDetails userDetails, Serializable targetId, String targetType, Object permission);
}
