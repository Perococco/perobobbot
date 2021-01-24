package perobobbot.data.schema;

import lombok.NonNull;

public class NopLogger implements Logger {

    @Override
    public void open() {}

    @Override
    public void close() {}

    @Override
    public void log(@NonNull String message) {}

    @Override
    public void log(@NonNull Throwable error) {}
}
