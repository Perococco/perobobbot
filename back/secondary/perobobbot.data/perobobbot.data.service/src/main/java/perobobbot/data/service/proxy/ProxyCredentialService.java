package perobobbot.data.service.proxy;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.data.service.CredentialService;

@RequiredArgsConstructor
public class ProxyCredentialService implements CredentialService {

    @Delegate
    private final CredentialService delegate;
}
