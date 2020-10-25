package perobobbot.program.echo;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.access.core.AccessRule;
import perobobbot.access.core.Policy;
import perobobbot.access.core.PolicyManager;
import perobobbot.chat.core.ChatController;
import perobobbot.common.lang.IO;
import perobobbot.common.lang.Role;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramFactoryBase;
import perobobbot.service.core.Requirement;
import perobobbot.service.core.Services;

import java.time.Duration;

public class EchoProgramFactory extends ProgramFactoryBase {

    public static final String PROGRAM_NAME = "echo";
    public static final ImmutableSet<Requirement> REQUIREMENTS = ImmutableSet.of(
            Requirement.required(IO.class, ChatController.class)
    );

    public EchoProgramFactory() {
        super(PROGRAM_NAME,REQUIREMENTS);
    }

    @Override
    public @NonNull Program create(@NonNull Services services, @NonNull PolicyManager policyManager) {
        final IO io = services.getService(IO.class);
        final Policy policy = policyManager.createPolicy(AccessRule.create(Role.ANY_USER, Duration.ofSeconds(10)));
        final ChatController chatController = services.getService(ChatController.class);

        return new EchoProgram(PROGRAM_NAME,io,chatController,policy);
    }
}
