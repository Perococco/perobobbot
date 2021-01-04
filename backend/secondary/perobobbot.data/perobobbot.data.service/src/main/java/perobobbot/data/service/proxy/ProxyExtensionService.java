package perobobbot.data.service.proxy;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.data.service.ExtensionService;

@RequiredArgsConstructor
public class ProxyExtensionService implements ExtensionService {

    @Delegate
    private final ExtensionService delegate;
}
