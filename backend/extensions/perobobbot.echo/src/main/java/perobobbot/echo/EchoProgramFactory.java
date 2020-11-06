package perobobbot.echo;

import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.common.lang.IO;
import perobobbot.common.lang.Role;
import perobobbot.common.messaging.Command;
import perobobbot.program.core.ProgramFactory;
import perobobbot.program.core.ProgramFactoryBase;
import perobobbot.services.Requirement;
import perobobbot.services.Services;

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