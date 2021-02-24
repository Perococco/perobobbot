package perobobbot.server.config.io;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.StandardInputProvider;
import perobobbot.lang.StandardInputReader;
import perobobbot.lang.Subscription;
import perobobbot.lang.fp.Consumer1;
import perobobbot.plugin.PluginService;

@RequiredArgsConstructor
public class SpringStandardInputReaderWrapper implements StandardInputProvider, PluginService {

    private final @NonNull StandardInputReader delegate;

    @Override
    public @NonNull Subscription addListener(@NonNull Consumer1<? super String> listener) {
        return delegate.addListener(listener);
    }

    public void start() {
        delegate.start();
    }

    public void requestStop() {
        delegate.requestStop();
    }
}
