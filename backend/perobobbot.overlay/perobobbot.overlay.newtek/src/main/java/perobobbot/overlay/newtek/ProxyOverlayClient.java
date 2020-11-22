package perobobbot.overlay.newtek;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perobobbot.overlay.api.OverlayClient;

@RequiredArgsConstructor
public class ProxyOverlayClient implements OverlayClient {

    @NonNull
    @Delegate
    @Getter(AccessLevel.PROTECTED)
    private final OverlayClient delegate;

}