package perobobbot.pause.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.IO;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.fp.Consumer1;
import perobobbot.pause.PauseExtension;

import java.time.Duration;

@NonNull
@RequiredArgsConstructor
public class ExecuteCommand implements Consumer1<ExecutionContext> {

    private final @NonNull IO io;

    private final @NonNull PauseExtension pauseExtension;

    @Override
    public void f(@NonNull ExecutionContext executionContext) {
        final var parameters = executionContext.getParameters().trim();
        if (parameters.equals("stop")) {
            pauseExtension.stopPause();
        } else {
            final var duration = parseArgument(executionContext.getParameters());
            pauseExtension.startPause(duration);
            io.send(pauseExtension.getUserId(), executionContext.getChannelInfo(), formPauseMessage(executionContext));
        }
    }

    private @NonNull String formPauseMessage(@NonNull ExecutionContext executionContext) {
        return executionContext.getMessageOwner().getHighlightedUserName()+" est en pause";//I18n TODO
    }

    private @NonNull Duration parseArgument(@NonNull String argument) {
        final double durationInSeconds = Math.max(0,Double.parseDouble(argument.trim())*60);
        return Duration.ofSeconds((int)(durationInSeconds+0.5));
    }
}
