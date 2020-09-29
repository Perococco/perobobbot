package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
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
    public ImmutableList<Program> getEnabledPrograms() {
        return getState().getEnabledPrograms();
    }

    @NonNull
    public boolean isEnabled(@NonNull String programName) {
        return getState().isEnabled(programName);
    }

    @NonNull
    public ImmutableSet<String> getNamesOfEnabledPrograms() {
        return getState().getNamesOfEnabledPrograms();
    }


    @NonNull
    public ImmutableSet<String> getProgramNames() {
        return getState().getProgramNames();
    }
}
