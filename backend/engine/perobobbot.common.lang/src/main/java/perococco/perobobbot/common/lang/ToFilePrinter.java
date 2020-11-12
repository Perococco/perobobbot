package perococco.perobobbot.common.lang;

import lombok.NonNull;
import perobobbot.common.lang.Printer;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

public class ToFilePrinter implements Printer {

    @NonNull
    private final PrintStream ps;

    public ToFilePrinter(@NonNull Path path) {
        try {
            ps = new PrintStream(path.toFile(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public @NonNull Printer println(@NonNull String value) {
        ps.println(value);
        return this;
    }

    @Override
    public void close() {
        ps.close();
    }
}
