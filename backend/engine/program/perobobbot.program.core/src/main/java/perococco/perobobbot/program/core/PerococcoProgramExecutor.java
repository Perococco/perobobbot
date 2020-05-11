package perococco.perobobbot.program.core;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import perobobbot.common.lang.ThrowableTool;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramExecutor;
import perococco.perobobbot.program.core.manager.*;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Log4j2
public class PerococcoProgramExecutor implements ProgramExecutor {

    private static final Marker PROGRAM = MarkerManager.getMarker("PROGRAM");

    private static final ScheduledExecutorService EXECUTOR_SERVICE = Executors.newScheduledThreadPool(2,
                                                                                                      new ThreadFactoryBuilder()
                                                                                                              .setNameFormat("ProgramExecutor Cleaner %d")
                                                                                                              .setDaemon(true)
                                                                                                              .build()
    );

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
                Program.create(managerIdentity)
                       .name("ProgramManager")
                       .addInstruction(StartProgram::new)
                       .addInstruction(StopProgram::new)
                       .addInstruction(ListPrograms::new)
                       .addInstruction(StartAllPrograms::new)
                       .addInstruction(StopAllPrograms::new)
                       .build()
        );

        EXECUTOR_SERVICE.scheduleAtFixedRate(this::cleanUp, 10, 10, TimeUnit.SECONDS);
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
        final String message = executionContext.getMessage();
        if (handle(prefixForManager, message, c -> this.handleManagerCommand(executionContext, c))) {
            return true;
        }
        return handle(prefixForPrograms, message, c -> this.handleProgramCommand(executionContext, c));
    }

    private boolean handle(@NonNull String prefix, @NonNull String message, Consumer1<? super InstructionExtraction> action) {
        final Optional<InstructionExtraction> commandExtraction = CommandExtractor.extract(prefix, message);
        commandExtraction.ifPresent(action);
        return commandExtraction.isPresent();
    }

    private void handleManagerCommand(@NonNull ExecutionContext executionContext, @NonNull InstructionExtraction instructionExtraction) {
        executeProgram(managerProgram, executionContext, instructionExtraction);
    }

    private void handleProgramCommand(@NonNull ExecutionContext executionContext, @NonNull InstructionExtraction instructionExtraction) {
        for (@NonNull Program enabledProgram : managerIdentity.enabledPrograms()) {
            if (!enabledProgram.hasInstruction(instructionExtraction.getInstructionName())) {
                continue;
            }
            if (enabledProgram.execute(executionContext, instructionExtraction.getInstructionName(), instructionExtraction.getParameters())) {
                break;
            }
        }
    }

    private void executeProgram(@NonNull Program program, @NonNull ExecutionContext executionContext, @NonNull InstructionExtraction instructionExtraction) {
        try {
            program.execute(executionContext,
                            instructionExtraction.getInstructionName(),
                            instructionExtraction.getParameters());
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.warn(PROGRAM, "Error while executing program '{}' : {}", program.getName(), t.getMessage());
        }

    }
}
