package perobobbot.greeter;

import lombok.NonNull;
import perobobbot.access.PolicyManager;
import perobobbot.common.lang.IO;
import perobobbot.common.messaging.ChatController;
import perobobbot.program.core.ProgramFactory;
import perobobbot.program.core.ProgramFactoryBase;
import perobobbot.services.Requirement;
import perobobbot.services.Services;
import perococco.perobobbot.greeter.GreeterProgram;

public class GreeterProgramFactory extends ProgramFactoryBase {

    public static final Requirement REQUIREMENT = Requirement.allOf(
            Requirement.allOf(IO.class,ChatController.class)
    );

    public static final String PROGRAM_NAME = "greeter";

    public GreeterProgramFactory() {
        super(PROGRAM_NAME, REQUIREMENT);
    }

    @Override
    public @NonNull ProgramFactory.Result create(@NonNull Services services, @NonNull PolicyManager policyManager) {
        final IO io = services.getService(IO.class);
        final ChatController chatController = services.getService(ChatController.class);
        return Result.withoutCommands(new GreeterProgram(getProgramName(), io, chatController));
    }

    @Override
    public boolean isAutoStart() {
        return true;
    }
}
