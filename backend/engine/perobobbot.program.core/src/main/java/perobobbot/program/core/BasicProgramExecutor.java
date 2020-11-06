package perobobbot.program.core;

import lombok.NonNull;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.common.lang.fp.Consumer2;

public class BasicProgramExecutor<P extends Program> extends ProgramExecutor<P> {

    private final @NonNull Consumer2<? super P, ? super ExecutionContext> action;

    public BasicProgramExecutor(@NonNull P program, @NonNull Consumer2<? super P, ? super ExecutionContext> action) {
        super(program);
        this.action = action;
    }

    public BasicProgramExecutor(@NonNull P program, @NonNull Consumer1<? super P> action) {
        super(program);
        this.action = (p,ctx) -> action.f(p);
    }

    @Override
    protected void doExecute(P program, ExecutionContext context) {
        action.f(program,context);
    }
}
