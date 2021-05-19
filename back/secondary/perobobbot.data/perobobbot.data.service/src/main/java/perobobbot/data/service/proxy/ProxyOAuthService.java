package perobobbot.data.service.proxy;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.data.service.OAuthService;

@RequiredArgsConstructor
public class ProxyOAuthService implements OAuthService {

    @Delegate
    private final OAuthService delegate;
}
