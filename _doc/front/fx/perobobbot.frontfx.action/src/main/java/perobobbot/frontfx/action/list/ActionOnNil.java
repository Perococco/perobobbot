package perobobbot.frontfx.action.list;

import lombok.NonNull;
import perobobbot.lang.Nil;

public abstract class ActionOnNil<R> extends BaseAction<Nil,R> {

    @Override
    public final R execute(@NonNull Nil parameter) throws Throwable {
        return execute();
    }

    protected abstract R execute() throws Throwable;
}
