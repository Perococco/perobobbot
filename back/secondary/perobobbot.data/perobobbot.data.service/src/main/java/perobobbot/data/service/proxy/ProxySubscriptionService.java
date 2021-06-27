package perobobbot.data.service.proxy;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.data.service.SubscriptionService;

@RequiredArgsConstructor
public class ProxySubscriptionService implements SubscriptionService {

    @Delegate
    private final @NonNull SubscriptionService delegate;
}
