package perobobbot.data.security.permission;

import lombok.NonNull;
import perobobbot.data.security.DataPermission;
import perobobbot.security.core.TargetedPermissionEvaluatorBase;

import java.security.Principal;

public abstract class DataEntityPermission extends TargetedPermissionEvaluatorBase<Principal, DataPermission> {

    public DataEntityPermission(@NonNull String targetType) {
        super(targetType, Principal.class, DataPermission.PARSER);
    }

}
