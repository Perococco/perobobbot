package perococco.perobobbot.service.core;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.service.core.Requirement;
import perobobbot.service.core.Services;

import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor
public class AllRequirements implements Requirement {

    @NonNull
    private final Requirement[] requirements;

    @Override
    public @NonNull ImmutableSet<Object> extractServices(@NonNull Services services) {
        return Arrays.stream(requirements)
                     .map(r -> r.extractServices(services))
                     .flatMap(Collection::stream)
                     .distinct()
                     .collect(ImmutableSet.toImmutableSet());
    }
}
