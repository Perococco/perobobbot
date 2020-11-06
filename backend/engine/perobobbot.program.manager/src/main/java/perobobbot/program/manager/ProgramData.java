package perobobbot.program.manager;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.messaging.Command;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramFactory;

@RequiredArgsConstructor
public class ProgramData<P extends Program> {

    @NonNull
    public static ProgramData from(@NonNull ProgramFactory.Result result, boolean autoStart) {
        return new ProgramData(result.getProgram(), autoStart, result.getCommands());
    }

    @Getter
    private final @NonNull P program;

    @Getter
    private final boolean autoStart;

    @Getter
    private final @NonNull ImmutableList<Command> commands;

    public @NonNull String getProgramName() {
        return program.getName();
    }
}
