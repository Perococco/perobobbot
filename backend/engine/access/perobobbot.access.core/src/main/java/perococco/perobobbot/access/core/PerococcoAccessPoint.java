package perococco.perobobbot.access.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.core.AccessInfoExtractor;
import perobobbot.access.core.AccessPoint;
import perobobbot.common.lang.User;
import perobobbot.common.lang.fp.Consumer1;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class PerococcoAccessPoint<P> implements AccessPoint<P> {

    @NonNull
    @Getter
    private final UUID id;

    @NonNull
    @Getter
    private final PerococcoPolicy policy;

    @NonNull
    private final Consumer1<? super P> action;

    @NonNull
    private final AccessInfoExtractor<P> accessInfoExtractor;

    @Override
    public void execute(@NonNull P parameter) {
        final User executor = accessInfoExtractor.getExecutor(parameter);
        final Instant executionTime = accessInfoExtractor.getExecutionTime(parameter);
        policy.run(executor,executionTime, () -> action.accept(parameter));
    }
}
