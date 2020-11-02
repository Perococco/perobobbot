package perobobbot.program.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.Executor;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.common.lang.fp.Consumer2;

@RequiredArgsConstructor
public abstract class ProgramExecutor<P extends Program> implements Executor<ExecutionContext> {

    private final @NonNull P program;

    @Override
    public final void execute(@NonNull ExecutionContext context) {
        if (program.isEnabled()) {
            doExecute(program,context);
        }
    }

    protected abstract void doExecute(P program, ExecutionContext context);



    public static <P extends Program> @NonNull ProgramExecutor<P> with(@NonNull P program, @NonNull Consumer1<? super P> action) {
        return with(program,(p,ctx) -> action.accept(p));
    }

    public static <P extends Program> @NonNull ProgramExecutor<P> with(@NonNull P program, @NonNull Consumer2<? super P, ? super ExecutionContext> action) {
        return new ProgramExecutor<P>(program) {
            @Override
            protected void doExecute(P program, ExecutionContext context) {
                action.accept(program,context);
            }
        };
    }


}
