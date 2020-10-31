package perobobbot.program.echo;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.access.core.AccessRule;
import perobobbot.access.core.Policy;
import perobobbot.access.core.PolicyManager;
import perobobbot.common.lang.IO;
import perobobbot.common.lang.Role;
import perobobbot.common.messaging.ChatCommand;
import perobobbot.common.messaging.ChatController;
import perobobbot.common.messaging.CommandBundleFactory;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramFactoryBase;
import perobobbot.service.core.Requirement;
import perobobbot.service.core.Services;

import java.time.Duration;

public class EchoProgramFactory extends ProgramFactoryBase {

    public static final String PROGRAM_NAME = "echo";
    public static final Requirement REQUIREMENT = Requirement.allOf(
            Requirement.allOf(IO.class),
            Requirement.atLeastOneOf(ChatController.class)
    );

    public EchoProgramFactory() {
        super(PROGRAM_NAME, REQUIREMENT);
    }

    @Override
    public @NonNull Program create(@NonNull Services services, @NonNull PolicyManager policyManager) {
        final IO io = services.getService(IO.class);
        final Policy policy = policyManager.createPolicy(AccessRule.create(Role.ANY_USER, Duration.ofSeconds(10)));
        final ChatController chatController = services.getService(ChatController.class);

        final CommandBundleFactory<EchoProgram> bundleFactory = CommandBundleFactory.with(
                chatController,
                p -> ImmutableList.of(
                        ChatCommand.simple("echo", policy.createAccessPoint(p::performEcho))
                )
        );

        return new EchoProgram(PROGRAM_NAME, bundleFactory, io);

    }
}