package perobobbot.program.manager;

import lombok.NonNull;
import perobobbot.access.PolicyManager;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.common.lang.fp.Function1;
import perobobbot.common.lang.fp.UnaryOperator1;
import perobobbot.common.messaging.Command;
import perobobbot.program.core.Program;
import perobobbot.program.core.UnknownProgram;
import perobobbot.services.Services;
import perococco.perobobbot.program.manager.ProgramRepositoryCreator;

import java.util.Optional;
import java.util.stream.Stream;

public interface ProgramRepository {

    static @NonNull ProgramRepository create(@NonNull Services services, @NonNull PolicyManager policyManager) {
        return ProgramRepositoryCreator.create(services,policyManager);
    }

    @NonNull
    ProgramRepository wrapProgramData(@NonNull UnaryOperator1<ProgramData<?>> wrapper);

    @NonNull
    ProgramRepository wrapProgram(@NonNull Function1<? super ProgramData<?>,? extends Program> wrapper);

    /**
     * @param commandName the name of the command
     * @return the command in an optional if it exists, an empty optional otherwise
     */
    @NonNull Optional<Command> findCommand(@NonNull String commandName);

    /**
     * @param programName the name of a program
     * @return an optional containing the program with the provided name, an empty optional if no program
     * with the provided name exists
     */
    @NonNull Optional<Program> findProgram(@NonNull String programName);

    default @NonNull Program getProgram(@NonNull String programName) {
        return findProgram(programName).orElseThrow(() -> new UnknownProgram(programName));
    }

    @NonNull Stream<Program> programStream();

    default void forEachProgram(@NonNull Consumer1<? super Program> consumer) {
        programStream().forEach(consumer);
    }

    void forEachProgramData(@NonNull Consumer1<? super ProgramData> consumer);
}
