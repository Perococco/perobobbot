package perococco.bot.program.core;

import bot.common.lang.Identity;
import bot.common.lang.ProxyIdentity;
import bot.program.core.Program;
import com.google.common.collect.ImmutableList;
import lombok.NonNull;

public class ManagerIdentity extends ProxyIdentity<ManagerState> {

    public ManagerIdentity(@NonNull Identity<ManagerState> delegate) {
        super(delegate);
    }

    public ManagerIdentity(@NonNull ManagerState initialState) {
        super(initialState);
    }

    @NonNull
    public ImmutableList<Program> enabledPrograms() {
        return getState().getEnabledPrograms();
    }
}
