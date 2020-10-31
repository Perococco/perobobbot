package perococco.perobobbot.access.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.core.AccessInfoExtractor;
import perobobbot.access.core.AccessPoint;
import perobobbot.access.core.AccessRule;
import perobobbot.access.core.Policy;
import perobobbot.common.lang.User;
import perobobbot.common.lang.fp.Consumer1;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class PerococcoPolicy implements Policy {

    @NonNull
    private final PerococcoPolicyManager policyManager;

    @Getter
    private final @NonNull UUID id;


    @Override
    public @NonNull Policy createChild(@NonNull AccessRule rule) {
        return policyManager.createChildPolicy(id, rule);
    }

    @Override
    public @NonNull <P> AccessPoint<P> createAccessPoint(@NonNull Consumer1<? super P> action, @NonNull AccessInfoExtractor<P> accessInfoExtractor) {
        return new PerococcoAccessPoint<P>(UUID.randomUUID(),this,action,accessInfoExtractor);
    }

    public void run(@NonNull User executor, @NonNull Instant executionTime, @NonNull Runnable toRunIfAllowed) {
        policyManager.run(id,executor,executionTime,toRunIfAllowed);
    }

}
