package perococco.perobobbot.program.manager;

import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramBase;
import perobobbot.program.core.ProgramInfo;
import perobobbot.program.manager.ProgramManager;
import perobobbot.program.manager.ProgramRepository;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PeroProgramManager extends ProgramBase implements ProgramManager {

    public static PeroProgramManager create(@NonNull ProgramRepository programRepository) {
        programRepository.forEachProgramData(pd -> {
            if (pd.isAutoStart()) {
                pd.getProgram().enable();
            }
        });
        return new PeroProgramManager(programRepository);
    }

    @NonNull
    private final ProgramRepository programRepository;

    @Override
    protected void onDisabled() {
        super.onDisabled();
        stopAll();
    }

    @Override
    public @NonNull String getName() {
        return "program-manager";
    }

    @Override
    public void startProgram(@NonNull String programName) {
        getProgram(programName).enable();
    }

    @Override
    public void stopProgram(@NonNull String programName) {
        getProgram(programName).disable();
    }

    @Override
    public void stopAll() {
        programRepository.forEachProgram(Program::disable);
    }

    @Override
    public void startAll() {
        programRepository.forEachProgram(Program::enable);
    }

    @NonNull
    private Program getProgram(@NonNull String programName) {
        return programRepository.getProgram(programName);
    }

    @Override
    public @NonNull ImmutableSet<ProgramInfo> programInfo() {
        return programRepository.programStream()
                                .map(Program::getInfo)
                                .collect(ImmutableSet.toImmutableSet());
    }


}
