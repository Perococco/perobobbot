package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramInfo;
import perobobbot.program.core.ProgramManager;
import perobobbot.program.core.UnknownProgram;

@Log4j2
@RequiredArgsConstructor
public class PerococcoProgramManager implements ProgramManager {

    @NonNull
    private final ImmutableMap<String, Program> programs;

    @Override
    public void startProgram(@NonNull String programName) {
        getProgram(programName).start();
    }

    @Override
    public void stopProgram(@NonNull String programName) {
        getProgram(programName).requestStop();
    }

    @Override
    public void stopAll() {
        programs.values().forEach(Program::requestStop);
    }

    @Override
    public void startAll() {
        programs.values().forEach(Program::start);
    }

    @NonNull
    private Program getProgram(@NonNull String programName) {
        final Program program = programs.get(programName);
        if (program == null) {
            throw new UnknownProgram(programName);
        }
        return program;
    }

    @Override
    public @NonNull ImmutableSet<ProgramInfo> programInfo() {
        return programs.values()
                       .stream()
                       .map(Program::getInfo)
                       .collect(ImmutableSet.toImmutableSet());
    }


}
