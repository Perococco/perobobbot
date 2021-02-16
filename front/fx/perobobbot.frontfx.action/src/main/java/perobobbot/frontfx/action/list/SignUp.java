package perobobbot.frontfx.action.list;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Secret;
import perobobbot.lang.Todo;

public class SignUp extends ActionNoResult<SignParameter> {

    @Override
    protected void doExecute(@NonNull SignParameter parameter) throws Throwable {
        Todo.TODO();
    }
}
