package perobobbot.service.core;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perococco.perobobbot.service.core.AllOf;
import perococco.perobobbot.service.core.AnyOf;
import perococco.perobobbot.service.core.AtLeastOneOf;

public interface Requirement {

    @NonNull
    static Requirement required(@NonNull Class<?>...services) {
        return new AllOf(ImmutableSet.copyOf(services));
    }

    @NonNull
    static Requirement atLeastOneOf(@NonNull Class<?>...services) {
        return new AtLeastOneOf(ImmutableSet.copyOf(services));
    }

    @NonNull
    static Requirement anyOf(@NonNull Class<?>...services) {
        return new AnyOf(ImmutableSet.copyOf(services));
    }

    @NonNull
    ImmutableSet<Object> extractServices(@NonNull Services services);

}
