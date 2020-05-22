package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.common.lang.Identity;
import perobobbot.common.lang.ProxyIdentity;
import perobobbot.program.core.Program;

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
