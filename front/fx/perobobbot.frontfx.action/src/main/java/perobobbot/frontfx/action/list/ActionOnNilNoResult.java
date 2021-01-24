package perobobbot.frontfx.action.list;

import lombok.NonNull;
import perobobbot.lang.Nil;

public abstract class ActionOnNilNoResult extends BaseAction<Nil,Nil> {

    @Override
    public final Nil execute(@NonNull Nil parameter) throws Throwable {
        execute();
        return Nil.NIL;
    }

    protected abstract void execute() throws Throwable;
}
