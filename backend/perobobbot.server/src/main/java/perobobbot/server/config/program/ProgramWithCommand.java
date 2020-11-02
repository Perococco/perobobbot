package perobobbot.server.config.program;

import lombok.NonNull;
import perobobbot.common.messaging.ChatController;
import perobobbot.program.core.Program;
import perobobbot.program.manager.ProgramData;

public class ProgramWithCommand extends ProgramWithCommandBase<Program> {

    public ProgramWithCommand(@NonNull ProgramData<?> programData, @NonNull ChatController chatController) {
        super(programData.getProgram(),programData.getCommands(), chatController);
    }

    @Override
    protected Program getThis() {
        return this;
    }
}
