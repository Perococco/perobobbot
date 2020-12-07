package perobobbot.lang;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class ProxyUser implements User {

    @Delegate
    private final User delegate;
}
