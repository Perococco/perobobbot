package perococco.perobobbot.common.lang.sync;

import lombok.NonNull;

interface SyncExecutorAction<I> {

    void execute(@NonNull SynExecutorLooper<I> context);
}
