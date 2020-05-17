package perobobbot.data.schema;

import lombok.NonNull;

public interface Logger extends AutoCloseable {

    void open();

    void close();

    void log(@NonNull String message);

    void log(@NonNull Throwable error);

    default void log(@NonNull String format, Object...args) {
        log(String.format(format, args));
    }
}
