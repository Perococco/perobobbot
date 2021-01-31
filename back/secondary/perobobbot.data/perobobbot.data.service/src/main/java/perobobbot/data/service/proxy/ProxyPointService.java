package perobobbot.data.service.proxy;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.data.service.PointService;


@RequiredArgsConstructor
public class ProxyPointService implements PointService {

    @Delegate
    private final @NonNull PointService delegate;
}
