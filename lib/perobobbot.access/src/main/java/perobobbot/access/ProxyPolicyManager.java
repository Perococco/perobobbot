package perobobbot.access;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

/**
 * A proxy for a {@link PolicyManager}
 */
@RequiredArgsConstructor
public class ProxyPolicyManager implements PolicyManager {

    @NonNull
    @Delegate
    private final PolicyManager delegate;

}
