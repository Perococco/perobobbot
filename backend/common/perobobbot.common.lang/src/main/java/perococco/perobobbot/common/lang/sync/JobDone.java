package perococco.perobobbot.common.lang.sync;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JobDone<I> implements SyncExecutorAction<I> {

    @NonNull
    private final I id;

    @Override
    public void execute(@NonNull SynExecutorLooper<I> context) {

    }
}
