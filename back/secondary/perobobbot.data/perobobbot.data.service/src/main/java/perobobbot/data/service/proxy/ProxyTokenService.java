package perobobbot.data.service.proxy;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.data.service.TokenService;

@RequiredArgsConstructor
public class ProxyTokenService implements TokenService {

    @Delegate
    private final TokenService delegate;
}
