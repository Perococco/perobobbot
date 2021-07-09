package perobobbot.data.service.proxy;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.data.service.ViewerIdentityService;

@RequiredArgsConstructor
public class ProxyViewerIdentityService implements ViewerIdentityService {

    @Delegate
    private final @NonNull ViewerIdentityService delegate;

}
