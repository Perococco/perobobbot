package perococco.perobobbot.program.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import perobobbot.common.lang.SyncExecutor;
import perobobbot.common.lang.ThreadFactories;
import perobobbot.common.lang.ThrowableTool;
import perobobbot.common.lang.fp.Function1;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramExecutor;
import perococco.perobobbot.program.core.manager.ManagerProgram;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Log4j2
public class PerococcoProgramExecutor implements ProgramExecutor {

    private static final Marker PROGRAM = MarkerManager.getMarker("PROGRAM");

    private static final ScheduledExecutorService CLEANER_EXECUTOR = Executors.newSingleThreadScheduledExecutor(
            ThreadFactories.daemon("ProgramExecutor Cleaner %d"));

    private final SyncExecutor<String> programExecutor = SyncExecutor.create("Program executor");

    @NonNull
    private final ManagerIdentity managerIdentity;

    @NonNull
    private final String prefixForManager;

    @NonNull
    private final String prefixForPrograms;

    private final ProgramWithPolicyHandling managerProgram;

    public PerococcoProgramExecutor(@NonNull String prefixForManager, @NonNull String prefixForPrograms) {
        this.managerIdentity = new ManagerIdentity(ManagerState.EMPTY);
        this.prefixForManager = prefixForManager;
        this.prefixForPrograms = prefixForPrograms;
        this.managerProgram = new ProgramWithPolicyHandling(
                new ManagerProgram(managerIdentity)
        );

        CLEANER_EXECUTOR.scheduleAtFixedRate(this::cleanUp, 1, 1, TimeUnit.MINUTES);
    }

    private void cleanUp() {
        managerProgram.cleanup();
        managerIdentity.getState().cleanUp();
    }

    @Override
    public void registerProgram(@NonNull Program program) {
        managerIdentity.mutate(s -> s.addProgram(new ProgramWithPolicyHandling(program)));
    }

    @Override
    public boolean handleMessage(@NonNull ExecutionContext executionContext) {
        final Optional<ProgramExecutionInfo> launcher =
                findProgramFromMessage(prefixForManager, executionContext, i -> Optional.of(managerProgram))
                        .or(() -> findProgramFromMessage(prefixForPrograms, executionContext, this::findEnabledProgramFromInstructionName));

        launcher.ifPresent(this::executeProgram);
        return launcher.isPresent();
    }

    @NonNull
    private Optional<ProgramExecutionInfo> findProgramFromMessage(
            @NonNull String prefix,
            @NonNull ExecutionContext executionContext,
            @NonNull Function1<? super String, ? extends Optional<Program>> programFromInstrumentNameFinder) {

        final Function1<InstructionExtraction, Optional<ProgramExecutionInfo>> finder =
                ie -> programFromInstrumentNameFinder.f(ie.getInstructionName())
                                                     .map(p -> new ProgramExecutionInfo(executionContext, ie, p));

        return CommandExtractor.extract(prefix, executionContext.getMessage())
                               .flatMap(finder);
    }


    @NonNull
    private Optional<Program> findEnabledProgramFromInstructionName(@NonNull String instructionName) {
        return managerIdentity.enabledPrograms()
                              .stream()
                              .filter(p -> p.hasInstruction(instructionName))
                              .findFirst();
    }

    private void executeProgram(@NonNull ProgramExecutionInfo programExecutionInfo) {
        try {
            programExecutor.submit(programExecutionInfo.getProgramName(), programExecutionInfo::launch);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.warn(PROGRAM, "Error while executing program '{}' : {}", programExecutionInfo.getProgramName(), t.getMessage());
        }

    }
}
