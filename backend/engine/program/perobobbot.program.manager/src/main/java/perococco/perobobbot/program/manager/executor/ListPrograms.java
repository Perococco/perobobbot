package perococco.perobobbot.program.manager.executor;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.Executor;
import perobobbot.common.lang.IO;
import perobobbot.program.core.ProgramInfo;
import perobobbot.program.manager.ProgramManager;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ListPrograms implements Executor<ExecutionContext> {

    @NonNull
    private final ProgramManager programManager;

    @NonNull
    private final IO io;


    @Override
    public void execute(@NonNull ExecutionContext context) {
        final String message = programManager.programInfo()
                      .stream()
                      .sorted()
                      .map(this::format)
                      .collect(Collectors.joining(", "));

        io.print(context.getChannelInfo(),message);
    }

    @NonNull
    private String format(@NonNull ProgramInfo programInfo) {
        return programInfo.getProgramName()+(programInfo.isRunning()?"*":"");
    }
}
