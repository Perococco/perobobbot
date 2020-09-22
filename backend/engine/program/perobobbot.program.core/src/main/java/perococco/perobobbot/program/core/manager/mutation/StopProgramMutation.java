package perococco.perobobbot.program.core.manager.mutation;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.Mutation;
import perobobbot.common.lang.SetTool;
import perobobbot.program.core.Program;
import perococco.perobobbot.program.core.ManagerState;

@RequiredArgsConstructor
public class StopProgramMutation implements Mutation<ManagerState> {

    @NonNull
    private final String programName;

    @Override
    public @NonNull ManagerState mutate(@NonNull ManagerState state) {
        final Program program = state.findProgram(programName).orElse(null);
        if (program == null || !state.isEnabled(programName)) {
            return state;
        }
        try {
            program.stop();
            final ImmutableSet<String> enabled = SetTool.remove(state.getNamesOfEnabledPrograms(),programName);
            return state.toBuilder().namesOfEnabledPrograms(enabled).build();
        } catch (Throwable t) {
            program.start();
            throw t;
        }
    }
}
