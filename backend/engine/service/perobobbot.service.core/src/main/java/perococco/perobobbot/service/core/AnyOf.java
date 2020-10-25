package perococco.perobobbot.service.core;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.service.core.Requirement;
import perobobbot.service.core.Services;

import java.util.Optional;

@RequiredArgsConstructor
public class AnyOf implements Requirement {

    @Getter
    private final ImmutableSet<Class<?>> requiredServices;

    @Override
    public @NonNull ImmutableSet<Object> extractServices(@NonNull Services services) {
        return requiredServices.stream()
                               .map(services::findService)
                               .flatMap(Optional::stream)
                               .collect(ImmutableSet.toImmutableSet());
    }
}
