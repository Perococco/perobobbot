package perobobbot.program.core;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.service.core.Services;

public interface ProgramFactory {

    @NonNull
    String programName();

    @NonNull
    Program create(@NonNull Services services);

    default boolean isAutoStart() {
        return false;
    }

    @NonNull
    default ImmutableSet<Class<?>> requiredServices() {
        return ImmutableSet.of();
    }

    @NonNull
    default ImmutableSet<Class<?>> optionalServices() {
        return ImmutableSet.of();
    }

}
