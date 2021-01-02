package perobobbot.security;

import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

public interface TargetedPermissionEvaluator extends PermissionEvaluator {

    @NonNull String getTargetType();

}
