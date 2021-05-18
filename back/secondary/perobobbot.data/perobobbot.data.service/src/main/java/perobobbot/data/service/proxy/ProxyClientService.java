package perobobbot.data.service.proxy;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.data.service.ClientService;

@RequiredArgsConstructor
public class ProxyClientService implements ClientService {

    @Delegate
    private final @NonNull ClientService clientService;

}
