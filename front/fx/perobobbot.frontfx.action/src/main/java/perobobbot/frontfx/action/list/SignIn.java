package perobobbot.frontfx.action.list;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import perobobbot.frontfx.model.ApplicationIdentity;
import perobobbot.frontfx.model.state.ApplicationState;
import perobobbot.frontfx.model.state.mutation.ClearAuthenticatedUser;
import perobobbot.frontfx.model.state.mutation.SetAuthenticatedUser;
import perobobbot.lang.Mutation;
import perobobbot.lang.fp.TryResult;
import perobobbot.rest.client.ClientManager;
import perobobbot.security.com.Credential;
import perobobbot.security.com.SimpleUser;

import java.util.concurrent.ExecutionException;

@Log4j2
@Component
@RequiredArgsConstructor
public class SignIn extends ActionNoResult<SignParameter> {

    private final @NonNull ClientManager clientManager;

    private final @NonNull ApplicationIdentity applicationIdentity;

    @Override
    protected void doExecute(@NonNull SignParameter parameter) throws Exception {
        parameter.validateAndCheck();

        final var mutation = clientManager.login(parameter.toCredential())
                                          .handle(TryResult::fromCompletionStage)
                                          .toCompletableFuture()
                                          .get()
                                          .merge(ClearAuthenticatedUser::create, SetAuthenticatedUser::create);

        applicationIdentity.mutate(mutation);

    }

}
