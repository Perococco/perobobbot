package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import perobobbot.common.lang.*;
import perobobbot.program.core.*;
import perobobbot.service.core.Services;
import perobobbot.service.core.ServicesFilter;
import perobobbot.service.core.UnknownService;
import perococco.perobobbot.program.core.manager.PerococcoProgramAction;
import perococco.perobobbot.program.core.manager.command.*;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Log4j2
public class PerococcoProgramExecutor implements ProgramExecutor {

    @NonNull
    public static ProgramExecutor create(@NonNull Services services) {
        final ImmutableSet.Builder<String> programToStart = ImmutableSet.builder();
        final ImmutableList.Builder<Program> programs = ImmutableList.builder();
        for (ProgramFactory programFactory : ServiceLoader.load(ProgramFactory.class)) {
            try {
                final Services filteredServices = ServicesFilter.filter(services,
                                                                        programFactory.requiredServices(),
                                                                        programFactory.optionalServices()
                );
                programs.add(programFactory.create(filteredServices));
                if (programFactory.isAutoStart()) {
                    programToStart.add(programFactory.programName());
                }
            } catch (UnknownService unknownService) {
                LOG.error("Could not create program '{}'. Missing required service : '{}'", programFactory.programName(), unknownService.getServiceType());
            }
        }
        return new PerococcoProgramExecutor(services, programs.build(), programToStart.build());
    }

    private static final Marker PROGRAM = MarkerManager.getMarker("PROGRAM");

    private static final ScheduledExecutorService CLEANER_EXECUTOR = Executors.newSingleThreadScheduledExecutor(
            ThreadFactories.daemon("ProgramExecutor Cleaner %d"));

    private final SyncExecutor<String> programExecutor = SyncExecutor.create("Program executor");

    @NonNull
    private final ManagerIdentity managerIdentity;

    @NonNull
    @Delegate
    private final ProgramAction programAction;

    @NonNull
    private final Program programManager;


    public PerococcoProgramExecutor(@NonNull Services services,
                                    @NonNull ImmutableList<Program> programs,
                                    @NonNull ImmutableSet<String> programsToStart) {
        this.managerIdentity = new ManagerIdentity(ManagerState.noneStarted(programs));
        this.programAction = new PerococcoProgramAction(services.getService(IO.class), managerIdentity);
        this.programManager = Program.builder(programAction)
                                     .setName("Program Manager")
                                     .setServices(services)
                                     .attachChatCommand("#list", ListPrograms::new)
                                     .attachChatCommand("#start-all", StartAllPrograms::new)
                                     .attachChatCommand("#stop-all", StopAllPrograms::new)
                                     .attachChatCommand("#stop", StopProgram::new)
                                     .attachChatCommand("#start", StartProgram::new)
                                     .build();

        programsToStart.forEach(programAction::startProgram);

        CLEANER_EXECUTOR.scheduleAtFixedRate(this::cleanUp, 1, 1, TimeUnit.MINUTES);
    }

    private void cleanUp() {
        programManager.cleanUp();
        managerIdentity.getState().cleanUp();
    }

    @Override
    public void handleMessage(@NonNull MessageContext messageContext) {
        final NamedExecution action = ExecutionContext.from(messageContext)
                                                      .flatMap(this::findCommandExecution)
                                                      .orElseGet(() -> formWatchers(messageContext));

        execute(action);
    }

    @NonNull
    private Optional<NamedExecution> findCommandExecution(@NonNull ExecutionContext executionContext) {
        return Stream.concat(
                Stream.of(programManager),
                managerIdentity.getEnabledPrograms().stream()
        )
                     .map(p -> findProgramCommand(p, executionContext))
                     .flatMap(Optional::stream)
                     .findFirst();
    }

    @NonNull
    private Optional<NamedExecution> findProgramCommand(@NonNull Program program, @NonNull ExecutionContext executionContext) {
        return program.findChatCommand(executionContext.getCommandName())
                      .map(c -> new NamedExecution(program.getName(), () -> c.execute(executionContext)));
    }

    private static final AtomicLong COUNTER = new AtomicLong(0);

    @NonNull
    private NamedExecution formWatchers(@NonNull MessageContext messageContext) {
        final ImmutableList<Program> programs = managerIdentity.getEnabledPrograms();
        final Runnable action = () -> {
            for (Program program : programs) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
                if (program.getMessageHandler().handleMessage(messageContext)) {
                    break;
                }
            }
        };
        return new NamedExecution("Watchers " + COUNTER.incrementAndGet(), action);
    }


    private void execute(@NonNull NamedExecution namedExecution) {
        try {
            programExecutor.submit(namedExecution.getName(), namedExecution);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.warn(PROGRAM, "Error while executing program '{}' : {}", namedExecution.getName(), t.getMessage());
        }

    }

    @Override
    public void stop() {
        programExecutor.requestStop();
    }

    @Override
    public void start() {
        programExecutor.start();
    }

}
