package perobobbot.security.core;

import lombok.NonNull;

public interface TargetedPermissionEvaluator extends PermissionEvaluator {

    @NonNull String getTargetType();

}
