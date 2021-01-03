package perobobbot.localio;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class LazyLocalExecutor implements LocalExecutor {

    private final Supplier<? extends LocalExecutor> supplier;

    public void handleMessage(@NonNull String line) {
        Optional.ofNullable(supplier.get()).ifPresent(l -> l.handleMessage(line));
    }
}
