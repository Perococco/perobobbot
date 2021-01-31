package perobobbot.lang;

import lombok.NonNull;

import java.time.Instant;

public interface InstantProvider {

    @NonNull Instant getNow();
}
