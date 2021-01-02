package perobobbot.security;

import lombok.NonNull;
import org.springframework.security.access.PermissionEvaluator;

public interface TargetedPermissionEvaluator extends PermissionEvaluator {

    @NonNull String getTargetType();

}
