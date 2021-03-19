package perobobbot.frontfx.action.list;

import lombok.NonNull;
import perobobbot.lang.Nil;

public abstract class ActionNoResult<P> extends BaseAction<P, Nil> {


    @Override
    public final Nil execute(@NonNull P parameter) throws Throwable {
        doExecute(parameter);
        return Nil.NIL;
    }

    protected abstract void doExecute(@NonNull P parameter) throws Throwable;
}
