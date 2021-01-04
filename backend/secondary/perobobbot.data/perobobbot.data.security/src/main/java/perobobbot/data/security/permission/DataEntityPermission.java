package perobobbot.data.security.permission;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import perobobbot.data.security.DataPermission;
import perobobbot.security.TargetedPermissionEvaluatorBase;

public abstract class DataEntityPermission extends TargetedPermissionEvaluatorBase<UserDetails, DataPermission> {

    public DataEntityPermission(@NonNull String targetType) {
        super(targetType, UserDetails.class, DataPermission.PARSER);
    }

}
