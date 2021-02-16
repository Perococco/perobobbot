package perobobbot.frontfx.action.list;

import lombok.NonNull;
import perobobbot.lang.Todo;

public class SignIn extends ActionNoResult<SignParameter> {

    @Override
    protected void doExecute(@NonNull SignParameter parameter) throws Throwable {
        Todo.TODO();
    }
}
