package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.access.core.AccessPoint;
import perobobbot.access.core.AccessRule;
import perobobbot.access.core.Policy;
import perobobbot.access.core.PolicyManager;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.IO;
import perobobbot.common.lang.Role;
import perobobbot.common.lang.ThrowableTool;
import perobobbot.common.messaging.ChatCommand;
import perobobbot.common.messaging.ChatController;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramFactory;
import perobobbot.program.core.ProgramManager;
import perobobbot.service.core.Requirement;
import perobobbot.service.core.Services;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProgramManagerFactory {

    public static final Requirement REQUIREMENT = Requirement.allOf(
            Requirement.allOf(IO.class),
            Requirement.optionallyAnyOf(ChatController.class)
    );

    @NonNull
    public static ProgramManager create(@NonNull Services services, @NonNull PolicyManager policyManager) {
        services.filter(REQUIREMENT);
        return new ProgramManagerFactory(services, policyManager).create();
    }

    @NonNull
    private final Services services;

    @NonNull
    private final PolicyManager policyManager;

    private ProgramManager programManager;

    private Policy policy;

    private ProgramManager create() {
        this.createProgramManager();
        this.createPolicy();
        this.setupChatCommands();
        return programManager;
    }

    private void createPolicy() {
        this.policy = policyManager.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));
    }

    private void createProgramManager() {
        this.programManager = new PerococcoProgramManager(loadPrograms());
    }

    @NonNull
    private ImmutableMap<String, Program> loadPrograms() {
        final Map<String, List<ProgramFactory>> programFactories = ServiceLoader.load(ProgramFactory.class)
                                                                        .stream()
                                                                        .map(ServiceLoader.Provider::get)
                                                                        .collect(Collectors.groupingBy(ProgramFactory::getProgramName, Collectors.toList()));

        final ImmutableMap.Builder<String, Program> programMap = ImmutableMap.builder();
        for (List<ProgramFactory> list : programFactories.values()) {
            final ProgramFactory programFactory = list.get(0);
            if (list.size() != 1) {
                LOG.warn("Multiple programs with name '" + programFactory.getProgramName() + "'. All are ignored ");
            } else {
                createProgram(programFactory).ifPresent(p -> programMap.put(p.getName(),p));
            }
        }
        return programMap.build();
    }

    @NonNull
    private Optional<Program> createProgram(@NonNull ProgramFactory programFactory) {
        try {
            final var filtered = services.filter(programFactory.getRequirement());
            final var program = programFactory.create(filtered,policyManager);
            if (programFactory.isAutoStart()) {
                program.enable();
            }
            return Optional.of(program);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            if (LOG.isDebugEnabled()) {
                LOG.warn("Could not create program '" + programFactory.getProgramName() + "'",t);
            } else {
                LOG.warn("Could not create program '" + programFactory.getProgramName() + "'");
            }
            return Optional.empty();
        }
    }

    private void setupChatCommands() {
        final ChatController chatController = services.findService(ChatController.class).orElse(null);
        if (chatController == null) {
            return;
        }
        final IO io = services.getService(IO.class);

        final AccessPoint<ExecutionContext> listProgram = policy.createAccessPoint(new ListPrograms(programManager, io));
        final AccessPoint<ExecutionContext> startProgram = policy.createAccessPoint(new StartProgram(programManager));
        final AccessPoint<ExecutionContext> stopProgram = policy.createAccessPoint(new StopProgram(programManager));
        final AccessPoint<ExecutionContext> startAllPrograms = policy.createAccessPoint(ctx -> programManager.startAll());
        final AccessPoint<ExecutionContext> stopAllPrograms = policy.createAccessPoint(ctx -> programManager.stopAll());

        chatController.addCommand(ChatCommand.simple("pm-list", listProgram));
        chatController.addCommand(ChatCommand.simple("pm-start", startProgram));
        chatController.addCommand(ChatCommand.simple("pm-stop", stopProgram));
        chatController.addCommand(ChatCommand.simple("pm-start-all", startAllPrograms));
        chatController.addCommand(ChatCommand.simple("pm-stop-all", stopAllPrograms));

    }


}
