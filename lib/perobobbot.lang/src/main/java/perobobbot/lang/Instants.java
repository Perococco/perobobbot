package perobobbot.lang;

import lombok.NonNull;

import java.time.Instant;

public interface Instants {

    int VERSION = 1;

    @NonNull Instant now();
}
