package perobobbot.pause.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.IO;
import perobobbot.command.CommandAction;
import perobobbot.command.CommandParsing;
import perobobbot.lang.ExecutionContext;
import perobobbot.pause.PauseExtension;

import java.time.Duration;

@NonNull
@RequiredArgsConstructor
public class LaunchPause implements CommandAction {

    private final @NonNull IO io;

    private final @NonNull PauseExtension pauseExtension;

    @Override
    public void execute(@NonNull CommandParsing parsing, @NonNull ExecutionContext context) {
        final double durationInSeconds = Math.max(0, parsing.getDoubleParameter("duration") * 60);
        final Duration duration = Duration.ofSeconds((int) (durationInSeconds + 0.5));
        pauseExtension.startPause(duration);
        io.send(context.getChatConnectionInfo(), context.getChannelName(), formPauseMessage(context));
    }

    private @NonNull String formPauseMessage(@NonNull ExecutionContext executionContext) {
        return executionContext.getMessageOwner().getHighlightedUserName() + " est en pause";//I18n TODO
    }

}
