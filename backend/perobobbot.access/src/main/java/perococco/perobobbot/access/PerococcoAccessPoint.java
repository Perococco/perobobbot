package perococco.perobobbot.access;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.AccessInfoExtractor;
import perobobbot.access.AccessPoint;
import perobobbot.lang.User;
import perobobbot.lang.fp.Consumer1;

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
