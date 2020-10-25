package perococco.perobobbot.service.core;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.service.core.Requirement;
import perobobbot.service.core.Services;
import perobobbot.service.core.UnsatisfiedRequirement;

@RequiredArgsConstructor
public class AllOf implements Requirement {

    @Getter
    private final ImmutableSet<Class<?>> requiredServices;

    @Override
    public @NonNull ImmutableSet<Object> extractServices(@NonNull Services services) {
        try {
            return requiredServices.stream()
                                   .map(services::getService)
                                   .collect(ImmutableSet.toImmutableSet());
        } catch (Throwable cause) {
            throw new UnsatisfiedRequirement(this,cause);
        }
    }

}
