package perococco.perobobbot.program.sample.echo.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.program.core.Execution;
import perobobbot.program.core.ExecutionContext;
import perococco.perobobbot.program.sample.echo.EchoAction;

@RequiredArgsConstructor
public class EchoCommand implements Execution {

    @NonNull
    private final EchoAction echoAction;

    @Override
    public void execute(@NonNull ExecutionContext executionContext) {
        echoAction.performEchoTo(executionContext.getChannelInfo(), executionContext.getMessageOwner(), executionContext.getParameters());
    }


}
