package perobobbot.program.greeter;

import lombok.NonNull;
import perobobbot.access.core.PolicyManager;
import perobobbot.chat.core.ChatController;
import perobobbot.common.lang.IO;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramFactoryBase;
import perobobbot.service.core.Requirement;
import perobobbot.service.core.Services;
import perococco.perobobbot.program.greeter.GreeterProgram;

public class GreeterProgramFactory extends ProgramFactoryBase {

    public static final Requirement REQUIREMENT = Requirement.allOf(
            Requirement.allOf(IO.class),
            Requirement.atLeastOneOf(ChatController.class)
    );

    public static final String PROGRAM_NAME = "greeter";

    public GreeterProgramFactory() {
        super(PROGRAM_NAME, REQUIREMENT);
    }

    @Override
    public @NonNull Program create(@NonNull Services services, @NonNull PolicyManager policyManager) {
        final IO io = services.getService(IO.class);
        final ChatController chatController = services.getService(ChatController.class);
        return new GreeterProgram(getProgramName(), io, chatController);
    }

    @Override
    public boolean isAutoStart() {
        return true;
    }
}
