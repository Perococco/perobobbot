package perobobbot.program.core;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.service.core.Services;

public interface ProgramFactory {

    @NonNull
    ImmutableSet<Class<? extends Object>> requiredServices();

    @NonNull
    ImmutableSet<Class<? extends Object>> optionalServices();

    Program create(@NonNull Services services);

}
