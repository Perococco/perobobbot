package perococco.perobobbot.service.core;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.service.core.Requirement;
import perobobbot.service.core.Services;
import perobobbot.service.core.UnsatisfiedRequirement;

import java.util.Optional;

@RequiredArgsConstructor
public class AtLeastOneOf implements Requirement {

    @Getter
    private final ImmutableSet<Class<?>> requiredServices;

    @Override
    public @NonNull ImmutableSet<Object> extractServices(@NonNull Services services) {
        final ImmutableSet<Object> s = requiredServices.stream()
                                                       .map(services::findService)
                                                       .flatMap(Optional::stream)
                                                       .collect(ImmutableSet.toImmutableSet());
        if (s.isEmpty()) {
            throw new UnsatisfiedRequirement(this);
        }
        return s;
    }
}
