package perobobbot.security;

import lombok.NonNull;

public interface TargetedPermissionEvaluator extends PermissionEvaluator {

    @NonNull String getTargetType();

}
