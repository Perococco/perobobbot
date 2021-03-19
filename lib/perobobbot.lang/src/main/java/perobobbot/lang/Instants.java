package perobobbot.lang;

import lombok.NonNull;

import java.time.Instant;

public interface Instants {

    public static final String VERSION = "1.0.0";

    @NonNull Instant now();
}
