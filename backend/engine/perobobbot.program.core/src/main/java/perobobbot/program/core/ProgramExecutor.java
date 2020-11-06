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


    public static <P extends Program> @NonNull ProgramExecutorBuilder<P> with(@NonNull P program) {
        return new ProgramExecutorBuilder<>(program);
    }

    @RequiredArgsConstructor
    public static class ProgramExecutorBuilder<P extends Program> {

        private final P program;

        public ProgramExecutor<P> execute(@NonNull Consumer1<? super P> action) {
            return new BasicProgramExecutor<>(program,action);
        }

        public ProgramExecutor<P> execute(@NonNull Consumer2<? super P, ? super ExecutionContext> action) {
            return new BasicProgramExecutor<>(program,action);
        }

    }

}
