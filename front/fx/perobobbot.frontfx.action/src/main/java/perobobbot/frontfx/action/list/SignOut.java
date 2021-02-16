package perobobbot.frontfx.action.list;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.ApplicationIdentity;
import perobobbot.frontfx.model.state.mutation.ClearAuthenticatedUser;

@Component
@RequiredArgsConstructor
public class SignOut extends ActionOnNilNoResult {

    private final @NonNull ApplicationIdentity applicationIdentity;

    @Override
    protected void execute() throws Throwable {
        applicationIdentity.mutate(ClearAuthenticatedUser.create());
    }
}
