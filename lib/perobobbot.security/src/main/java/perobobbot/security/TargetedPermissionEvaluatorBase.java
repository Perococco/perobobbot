package perobobbot.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class TargetedPermissionEvaluatorBase implements TargetedPermissionEvaluator {

    private final @NonNull Class<?> targetType;

    @Override
    public @NonNull String getTargetType() {
        return targetType.getSimpleName();
    }

}
