package perobobbot.frontfx.action.list;

import lombok.NonNull;
import lombok.Value;
import org.springframework.stereotype.Component;
import perobobbot.lang.Secret;
import perobobbot.lang.Todo;

@Component
public class SignUp extends ActionNoResult<SignParameter> {

    @Override
    protected void doExecute(@NonNull SignParameter parameter) throws Throwable {
        Todo.TODO();
    }
}
