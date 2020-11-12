package perococco.perobobbot.common.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.Printer;

import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
public class DoubleHeaderPrinter implements Printer {

    private final AtomicBoolean isFirstHeader = new AtomicBoolean(true);

    @NonNull
    private final Printer delegate;

    @NonNull
    private final String firstHeader;

    @NonNull
    private final String afterHeader;

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public @NonNull Printer println(@NonNull String value) {
        final String header = isFirstHeader.getAndSet(false)?firstHeader:afterHeader;
        delegate.println(header+value);
        return this;
    }
}
