package perobobbot.security;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.DenyAllPermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

@RequiredArgsConstructor
public class PermissionEvaluatorDispatcher implements PermissionEvaluator {

    private final ImmutableMap<String,TargetedPermissionEvaluator> evaluators;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        return retrievePermissionEvaluator(targetDomainObject.getClass().getSimpleName())
                .hasPermissionWithObject(authentication,targetDomainObject,permission);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return retrievePermissionEvaluator(targetType)
                .hasPermissionWithId(authentication,targetId,permission);
    }

    private @NonNull perobobbot.security.PermissionEvaluator retrievePermissionEvaluator(@NonNull String targetType) {
        final var evaluator = evaluators.get(targetType);
        if (evaluator == null) {
            return perobobbot.security.PermissionEvaluator.DENY_ALL;
        }
        return evaluator;
    }

}
