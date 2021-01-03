package perobobbot.echo.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.command.CommandAction;
import perobobbot.command.CommandParsing;
import perobobbot.echo.EchoExtension;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.fp.Consumer1;

@RequiredArgsConstructor
public class EchoExecutor implements CommandAction {

    private final @NonNull EchoExtension echoExtension;

    @Override
    public void execute(@NonNull CommandParsing parsing, @NonNull ExecutionContext ctx) {
        if (ctx.isMessageFromMe()) {
            return;
        }
        echoExtension.performEcho(
                ctx.getChatConnectionInfo(),
                ctx.getChannelName(),
                ctx.getMessageOwner(),
                ctx.getContent());
    }
}
