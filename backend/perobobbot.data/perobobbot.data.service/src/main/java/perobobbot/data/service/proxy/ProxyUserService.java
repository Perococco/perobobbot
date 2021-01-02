package perobobbot.data.service.proxy;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.data.service.UserService;

@RequiredArgsConstructor
public class ProxyUserService implements UserService {

    @Delegate
    private final UserService delegate;
}
