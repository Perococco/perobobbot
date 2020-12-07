package perococco.perobobbot.common.lang;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Printer;

@RequiredArgsConstructor
public class SingleHeaderPrinter implements Printer {

    @NonNull
    private final Printer delegate;

    @NonNull
    private final String header;

    @Override
    public void close() {
        delegate.close();
    }

    @Override
    public @NonNull Printer println(@NonNull String value) {
        delegate.println(header+value);
        return this;
    }
}
