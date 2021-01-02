package perobobbot.echo.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.echo.EchoExtension;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.fp.Consumer1;

@RequiredArgsConstructor
public class EchoExecutor implements Consumer1<ExecutionContext> {

    private final @NonNull EchoExtension echoExtension;

    @Override
    public void f(@NonNull ExecutionContext ctx) {
        if (ctx.isMessageFromMe()) {
            return;
        }
        echoExtension.performEcho(
                ctx.getBot(),
                ctx.getChannelInfo(),
                ctx.getMessageOwner(),
                ctx.getContent());
    }
}
