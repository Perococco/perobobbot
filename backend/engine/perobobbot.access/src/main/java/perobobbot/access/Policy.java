package perobobbot.access;

import lombok.NonNull;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.Executor;
import perobobbot.lang.fp.Consumer1;
import perococco.perobobbot.access.ProviderFromContext;

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
