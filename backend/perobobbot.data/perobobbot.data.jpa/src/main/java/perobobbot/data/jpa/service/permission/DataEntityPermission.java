package perobobbot.data.jpa.service.permission;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import perobobbot.data.jpa.service.DataOperation;
import perobobbot.security.UserDetailTargetedPermissionEvaluator;

import java.io.Serializable;

public abstract class DataEntityPermission extends UserDetailTargetedPermissionEvaluator {

    public DataEntityPermission(@NonNull Class<?> targetType) {
        super(targetType);
    }

    private @NonNull DataOperation parsePermission(Object permission) {
        if (permission instanceof DataOperation) {
            return (DataOperation) permission;
        }
        return DataOperation.getOperation(permission.toString());
    }

    @Override
    final protected boolean hasPermission(@NonNull UserDetails userDetails, Object targetDomainObject, Object permission) {
        return hasPermission(userDetails,targetDomainObject,parsePermission(permission));
    }

    @Override
    final protected boolean hasPermission(@NonNull UserDetails userDetails, Serializable targetId, String targetType, Object permission) {
        return hasPermission(userDetails,targetId,targetType,parsePermission(permission));
    }

    protected abstract boolean hasPermission(@NonNull UserDetails userDetails, Object targetDomainObject, DataOperation permission);
    protected abstract boolean hasPermission(@NonNull UserDetails userDetails, Serializable targetId, String targetType, DataOperation permission);
}
