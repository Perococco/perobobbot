package perobobbot.program.core;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.service.core.Services;

public interface ProgramFactory {

    /**
     * Used to get the name of the program before creating it
     * @return the name of program.
     */
    @NonNull
    String programName();

    /**
     * @return the set of services required by the program created by this factory
     */
    @NonNull
    default ImmutableSet<Class<?>> requiredServices() {
        return ImmutableSet.of();
    }

    /**
     * @return the set of services that not required but used by the program created by this factory
     */
    @NonNull
    default ImmutableSet<Class<?>> optionalServices() {
        return ImmutableSet.of();
    }

    /**
     * @param services the service provider but with only the service listed by {@link #requiredServices()} and {@link #optionalServices()}
     * @return a new instance of the program
     */
    @NonNull
    Program create(@NonNull Services services);

    /**
     * @return true if the program created by this factory should be started automatically
     */
    default boolean isAutoStart() {
        return true;
    }

}
