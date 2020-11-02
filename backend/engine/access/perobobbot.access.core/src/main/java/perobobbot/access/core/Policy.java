package perobobbot.access.core;

import lombok.NonNull;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.Executor;
import perobobbot.common.lang.fp.Consumer1;
import perococco.perobobbot.access.core.ProviderFromContext;

import java.util.UUID;

public interface Policy {

    @NonNull
    UUID getId();

    @NonNull
    Policy createChild(@NonNull AccessRule rule);

    @NonNull
    <P> AccessPoint<P> createAccessPoint(@NonNull Consumer1<? super P> action, @NonNull AccessInfoExtractor<P> accessInfoExtractor);

    @NonNull
    default AccessPoint<ExecutionContext> createAccessPoint(@NonNull Consumer1<? super ExecutionContext> action) {
        return createAccessPoint(action,new ProviderFromContext());
    }

    @NonNull
    default AccessPoint<ExecutionContext> createAccessPoint(@NonNull Executor<? super ExecutionContext> action) {
        return createAccessPoint(action::execute,new ProviderFromContext());
    }

}
