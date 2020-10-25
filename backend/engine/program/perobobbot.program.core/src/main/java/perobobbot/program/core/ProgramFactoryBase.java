package perobobbot.program.core;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.service.core.Requirement;

@RequiredArgsConstructor
public abstract class ProgramFactoryBase implements ProgramFactory{

    @NonNull
    @Getter
    private final String programName;

    @NonNull
    @Getter
    private final ImmutableSet<Requirement> requirements;

}
