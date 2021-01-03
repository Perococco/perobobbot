package perobobbot.access;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

/**
 * A proxy for a {@link ExecutionManager}
 */
@RequiredArgsConstructor
public class ProxyExecutionManager implements ExecutionManager {

    @NonNull
    @Delegate
    private final ExecutionManager delegate;

}
