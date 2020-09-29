package perococco.perobobbot.program.core.manager.mutation;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.common.lang.Mutation;
import perobobbot.program.core.Program;
import perococco.perobobbot.program.core.ManagerState;

public class StartAllProgramMutation implements Mutation<ManagerState> {

    @Override
    public @NonNull ManagerState mutate(@NonNull ManagerState state) {
        final ImmutableList<Program> disabledPrograms = state.getDisabledPrograms();
        try {
            disabledPrograms.forEach(Program::start);
            return state.toBuilder().namesOfEnabledPrograms(state.getProgramNames()).build();
        } catch (Throwable t) {
            disabledPrograms.forEach(Program::stop);
            throw t;
        }
    }
}
