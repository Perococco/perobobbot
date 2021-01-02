package perobobbot.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import perobobbot.lang.Parser;

import java.io.Serializable;

@RequiredArgsConstructor
public abstract class TargetedPermissionEvaluatorBase<A,P> implements TargetedPermissionEvaluator {

    private final @NonNull String targetType;

    private final @NonNull Class<A> principalType;

    private final @NonNull Parser<P> permissionParser;

    @Override
    public @NonNull String getTargetType() {
        return targetType;
    }

    @Override
    public final boolean hasPermissionWithObject(Authentication authentication, Object targetDomainObject, Object permission) {
        final P perm = extractPermission(permission);
        final A principal = extractPrincipal(authentication);
        if (perm == null || principal == null) {
            return false;
        }
        return hasPermissionWithObject(principal,targetDomainObject,perm);
    }

    @Override
    public final boolean hasPermissionWithId(Authentication authentication, Serializable targetId, Object permission) {
        final P perm = extractPermission(permission);
        final A principal = extractPrincipal(authentication);
        if (perm == null || principal == null) {
            return false;
        }
        return hasPermissionWithId(principal,targetId,perm);
    }

    protected abstract boolean hasPermissionWithObject(@NonNull A principal, Object targetDomainObject, @NonNull P permission);

    protected abstract boolean hasPermissionWithId(@NonNull A principal, Serializable targetId, @NonNull P permission);

    private P extractPermission(@NonNull Object permission) {
        if (permissionParser.targetType().isInstance(permission)) {
            return permissionParser.targetType().cast(permission);
        }
        return permissionParser.parse(String.valueOf(permission)).success().orElse(null);
    }

    private A extractPrincipal(Authentication authentication) {
        final Object principal = authentication == null ? null : authentication.getPrincipal();
        if (principal == null || !authentication.isAuthenticated()) {
            return null;
        }
        if (principalType.isInstance(principal)) {
            return principalType.cast(principal);
        }
        return null;
    }

}
