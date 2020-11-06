package perobobbot.services;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perococco.perobobbot.services.AllOf;
import perococco.perobobbot.services.AllRequirements;
import perococco.perobobbot.services.AnyOf;
import perococco.perobobbot.services.AtLeastOneOf;

public interface Requirement {

    static Requirement allOf(@NonNull Requirement... requirements) {
        return new AllRequirements(requirements);
    }

    @NonNull
    static Requirement allOf(@NonNull Class<?>...services) {
        return new AllOf(ImmutableSet.copyOf(services));
    }

    @NonNull
    static Requirement atLeastOneOf(@NonNull Class<?>...services) {
        return new AtLeastOneOf(ImmutableSet.copyOf(services));
    }

    @NonNull
    static Requirement optionallyAnyOf(@NonNull Class<?>...services) {
        return new AnyOf(ImmutableSet.copyOf(services));
    }

    @NonNull
    ImmutableSet<Object> extractServices(@NonNull Services services);

}
