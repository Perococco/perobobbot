package perococco.perobobbot.program.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.IO;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.program.core.ProgramInfo;
import perobobbot.program.core.ProgramManager;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ListPrograms implements Consumer1<ExecutionContext> {

    @NonNull
    private final ProgramManager programManager;

    @NonNull
    private final IO io;

    @Override
    public void f(@NonNull ExecutionContext executionContext) {
        final String message = programManager.programInfo()
                      .stream()
                      .sorted()
                      .map(this::format)
                      .collect(Collectors.joining(", "));
        io.print(executionContext.getChannelInfo(),message);
    }

    @NonNull
    private String format(@NonNull ProgramInfo programInfo) {
        return programInfo.getProgramName()+(programInfo.isRunning()?"*":"");
    }
}
