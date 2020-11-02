package perobobbot.server.config.program;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.common.messaging.ChatController;
import perobobbot.program.core.ProgramInfo;
import perobobbot.program.manager.ProgramData;
import perobobbot.program.manager.ProgramManager;

public class ProgramManagerWithCommand extends ProgramWithCommandBase<ProgramManager> implements ProgramManager {

    public ProgramManagerWithCommand(@NonNull ProgramData<ProgramManager> programData, @NonNull ChatController chatController) {
        super(programData, chatController);
    }

    @Override
    protected ProgramManager getThis() {
        return this;
    }

    @Override
    public void startProgram(@NonNull String programName) {
        getDelegate().startProgram(programName);
    }

    @Override
    public void stopProgram(@NonNull String programName) {
        getDelegate().stopProgram(programName);
    }

    @Override
    public void stopAll() {
        getDelegate().stopAll();
    }

    @Override
    public void startAll() {
        getDelegate().startAll();
    }

    @Override
    public @NonNull ImmutableSet<ProgramInfo> programInfo() {
        return getDelegate().programInfo();
    }
}
