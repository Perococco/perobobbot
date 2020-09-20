package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
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
import java.util.concurrent.atomic.AtomicLong;

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
    private final ImmutableList<Prefix> prefixes;

    private final ProgramWithPolicyHandling managerProgram;

    public PerococcoProgramExecutor(@NonNull String prefixForManager, @NonNull String prefixForPrograms) {
        this.managerIdentity = new ManagerIdentity(ManagerState.EMPTY);
        this.managerProgram = new ProgramWithPolicyHandling(new ManagerProgram(managerIdentity));

        this.prefixes = ImmutableList.of(new Prefix(prefixForManager, i -> Optional.of(managerProgram)),
                                         new Prefix(prefixForPrograms, this::findEnabledProgramFromInstructionName));

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
    public void handleMessage(@NonNull ExecutionContext executionContext) {
        final NamedExecution namedExecution = prefixes.stream()
                                                      .map(p -> p.parse(executionContext))
                                                      .flatMap(Optional::stream)
                                                      .findAny()
                                                      .orElseGet(() -> formWatchers(executionContext));
        execute(namedExecution);
    }

    @NonNull
    private Optional<Program> findEnabledProgramFromInstructionName(@NonNull String instructionName) {
        return managerIdentity.enabledPrograms()
                              .stream()
                              .filter(p -> p.hasInstruction(instructionName))
                              .findFirst();
    }

    private final AtomicLong COUNTER = new AtomicLong(0);

    @NonNull
    private NamedExecution formWatchers(@NonNull ExecutionContext executionContext) {
        final ImmutableList<Program> programs = managerIdentity.enabledPrograms();
        final String name = "watchers"+COUNTER.getAndIncrement();
        return new NamedExecution() {

            @Override
            public @NonNull String getName() {
                return name;
            }

            @Override
            public void launch() {
                ExecutionContext ctx = executionContext;
                for (Program program : programs) {
                    ctx = program.handleMessage(ctx);
                    if (ctx.isConsumed()) {
                        return;
                    }
                }
            }
        };
    }




    private void execute(@NonNull NamedExecution namedExecution) {
        try {
            programExecutor.submit(namedExecution.getName(), namedExecution);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.warn(PROGRAM, "Error while executing program '{}' : {}", namedExecution.getName(), t.getMessage());
        }

    }

    @RequiredArgsConstructor
    private static class Prefix {

        @NonNull
        @Getter
        private final String value;

        @NonNull
        private final Function1<? super String, ? extends Optional<Program>> programFinder;

        @NonNull
        public Optional<Program> findEnabledProgramFromInstructionName(@NonNull String instructionName) {
            return programFinder.f(instructionName);
        }

        @NonNull
        public Optional<NamedExecution> parse(@NonNull ExecutionContext context) {
            final InstructionExtraction instructionExtraction = CommandExtractor.extract(value, context.getMessage()).orElse(null);
            if (instructionExtraction == null) {
                return Optional.empty();
            }
            return programFinder.f(instructionExtraction.getInstructionName())
                                .map(p -> new ProgramExecutionInfo(context, instructionExtraction, p));
        }
    }
}
