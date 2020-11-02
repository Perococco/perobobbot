package perobobbot.program.echo;

import lombok.NonNull;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.program.core.ProgramExecutor;

public class EchoExecutor extends ProgramExecutor<EchoProgram> {

    public EchoExecutor(@NonNull EchoProgram program) {
        super(program);
    }

    @Override
    protected void doExecute(EchoProgram program, ExecutionContext ctx) {
        if (ctx.isMessageFromMe()) {
            return;
        }
        program.performEcho(ctx.getChannelInfo(),
                            ctx.getMessageOwner(),
                            ctx.getContent());
    }
}
