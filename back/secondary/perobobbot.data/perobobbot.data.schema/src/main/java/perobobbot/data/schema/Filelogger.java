package perobobbot.data.schema;

import lombok.NonNull;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Optional;

public class Filelogger implements Logger {

    private PrintStream ps;

    @Override
    public void open() {
        try {
            final Path path = Path.of(System.getProperty("user.home")).resolve("ScriptExporter_Trace.txt");
            ps = new PrintStream(path.toFile(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            ps = null;
        }
    }

    @Override
    public void log(@NonNull String message) {
        Optional.ofNullable(ps).ifPresent(ps -> ps.println(message));
    }

    @Override
    public void log(@NonNull Throwable error) {
        Optional.ofNullable(ps).ifPresent(error::printStackTrace);
    }

    @Override
    public void close() {
        try {
            if (ps != null) {
                ps.close();
            }
        } finally {
            ps = null;
        }
    }
}
