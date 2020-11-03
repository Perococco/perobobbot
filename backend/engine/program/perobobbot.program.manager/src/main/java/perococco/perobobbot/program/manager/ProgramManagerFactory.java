package perococco.perobobbot.program.manager;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.access.core.AccessRule;
import perobobbot.access.core.Policy;
import perobobbot.access.core.PolicyManager;
import perobobbot.common.lang.IO;
import perobobbot.common.lang.Role;
import perobobbot.common.messaging.ChatController;
import perobobbot.common.messaging.Command;
import perobobbot.program.manager.ProgramData;
import perobobbot.program.manager.ProgramManager;
import perobobbot.program.manager.ProgramRepository;
import perobobbot.service.core.Requirement;
import perobobbot.service.core.Services;
import perococco.perobobbot.program.manager.executor.*;

import java.time.Duration;

import static perobobbot.common.messaging.Command.complex;
import static perobobbot.common.messaging.Command.simple;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProgramManagerFactory {

    @NonNull
    public static ProgramData<ProgramManager> create(@NonNull Services services, @NonNull PolicyManager policyManager, @NonNull ProgramRepository programRepository) {
        return new ProgramManagerFactory(services, policyManager, programRepository).create();
    }


    public static final Requirement REQUIREMENT = Requirement.allOf(
            Requirement.allOf(IO.class),
            Requirement.optionallyAnyOf(ChatController.class)
    );

    @NonNull
    private final Services services;

    @NonNull
    private final PolicyManager policyManager;

    @NonNull
    private final ProgramRepository programRepository;

    private ProgramManager programManager;

    private Policy policy;

    private Command command;

    @NonNull
    private ProgramData<ProgramManager> create() {
        this.createProgramManager();
        this.createPolicy();
        this.createCommand();
        return new ProgramData<>(programManager, true, ImmutableList.of(command));
    }

    private void createProgramManager() {
        this.programManager = PeroProgramManager.create(programRepository);
    }


    private void createPolicy() {
        this.policy = policyManager.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));
    }

    private void createCommand() {
        final IO io = services.getService(IO.class);

        this.command = complex("pm",
                               simple("list", policy.createAccessPoint(new ListPrograms(programManager, io))),
                               simple("start", policy.createAccessPoint(new StartProgram(programManager))),
                               simple("stop", policy.createAccessPoint(new StopProgram(programManager))),
                               simple("start-all", policy.createAccessPoint(new StartAllPrograms(programManager))),
                               simple("stop-all", policy.createAccessPoint(new StopAllPrograms(programManager)))
        );
    }

}
