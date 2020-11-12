package perobobbot.lang;

import lombok.NonNull;

import java.time.Instant;

public interface DispatchContext {


    @NonNull Instant getDispatchingTime();


    DispatchContext NIL = () -> Instant.EPOCH;

}
