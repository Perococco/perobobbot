package perobobbot.access.core;

import lombok.NonNull;
import perobobbot.common.lang.Executor;

import java.util.UUID;

public interface AccessPoint<P> extends Executor<P> {

    @NonNull
    UUID getId();

    void execute(@NonNull P parameter);

}
