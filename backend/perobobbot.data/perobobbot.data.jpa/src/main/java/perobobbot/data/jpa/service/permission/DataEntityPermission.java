package perobobbot.data.jpa.service.permission;

import lombok.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import perobobbot.data.jpa.service.DataPermission;
import perobobbot.security.TargetedPermissionEvaluatorBase;

import java.io.Serializable;

public abstract class DataEntityPermission extends TargetedPermissionEvaluatorBase<UserDetails, DataPermission> {

    public DataEntityPermission(@NonNull Class<?> targetType) {
        super(targetType,UserDetails.class,DataPermission.PARSER);
    }

}
