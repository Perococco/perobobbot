package perobobbot.program.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class ProxyProgram implements Program {

    @NonNull
    @Delegate
    private final Program delegate;
}
