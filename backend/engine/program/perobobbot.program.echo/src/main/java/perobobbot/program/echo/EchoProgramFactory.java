package perobobbot.program.echo;

import lombok.NonNull;
import perobobbot.access.core.AccessRule;
import perobobbot.access.core.Policy;
import perobobbot.access.core.PolicyManager;
import perobobbot.common.lang.IO;
import perobobbot.common.lang.Role;
import perobobbot.common.messaging.Command;
import perobobbot.program.core.ProgramFactory;
import perobobbot.program.core.ProgramFactoryBase;
import perobobbot.service.core.Requirement;
import perobobbot.service.core.Services;

import java.time.Duration;

public class EchoProgramFactory extends ProgramFactoryBase {

    public static final String PROGRAM_NAME = "echo";
    public static final Requirement REQUIREMENT = Requirement.allOf(
            Requirement.allOf(IO.class)
    );

    public EchoProgramFactory() {
        super(PROGRAM_NAME, REQUIREMENT);
    }

    @Override
    public @NonNull ProgramFactory.Result create(@NonNull Services services, @NonNull PolicyManager policyManager) {
        final IO io = services.getService(IO.class);
        final Policy policy = policyManager.createPolicy(AccessRule.create(Role.ANY_USER, Duration.ofSeconds(10)));

        final EchoProgram program = new EchoProgram(PROGRAM_NAME, io);
        final Command command = Command.simple("echo", policy.createAccessPoint(new EchoExecutor(program)));

        return Result.withOneCommand(program, command);
    }
}