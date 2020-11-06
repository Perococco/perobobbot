package perococco.perobobbot.services;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.services.Requirement;
import perobobbot.services.Services;
import perobobbot.services.UnsatisfiedRequirement;

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
