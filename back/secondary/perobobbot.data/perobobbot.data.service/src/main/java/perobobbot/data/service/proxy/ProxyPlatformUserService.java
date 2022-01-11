package perobobbot.data.service.proxy;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.data.service.PlatformUserService;

@RequiredArgsConstructor
public class ProxyPlatformUserService implements PlatformUserService {

    @Delegate
    private final @NonNull PlatformUserService delegate;

}
