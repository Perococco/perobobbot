package perococco.perobobbot.program.core.manager.mutation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.common.lang.Mutation;
import perobobbot.program.core.Program;
import perococco.perobobbot.program.core.ManagerState;

public class StopAllProgramMutation implements Mutation<ManagerState> {

    @Override
    public @NonNull ManagerState mutate(@NonNull ManagerState state) {
        final ImmutableList<Program> enabledPrograms = state.getEnabledPrograms();
        try {
            enabledPrograms.forEach(Program::stop);
            return state.toBuilder().namesOfEnabledPrograms(ImmutableSet.of()).build();
        } catch (Throwable t) {
            enabledPrograms.forEach(Program::start);
            throw t;
        }
    }
}
