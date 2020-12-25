package perobobbot.physics;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class ProxyEntity2D implements Entity2D {

    @NonNull
    @Getter
    @Delegate(types = {Entity2D.class, ROEntity2D.class})
    private final Entity2D delegate;
}
