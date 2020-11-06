package perobobbot.program.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.services.Requirement;

@RequiredArgsConstructor
public abstract class ProgramFactoryBase implements ProgramFactory {

    @NonNull
    @Getter
    private final String programName;

    @NonNull
    @Getter
    private final Requirement requirement;

}
