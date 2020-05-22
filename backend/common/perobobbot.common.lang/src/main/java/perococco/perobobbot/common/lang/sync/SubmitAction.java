package perococco.perobobbot.common.lang.sync;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
public class SubmitAction<I> implements SyncExecutorAction<I> {

    @NonNull
    private final I id;

    @NonNull
    private final Runnable action;

    @Override
    public void execute(@NonNull SynExecutorLooper<I> context) {
        context.addRunnable(id,action);
        context.ifPossibleExecuteForId(id);
    }
}
