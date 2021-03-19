package perococco.perobobbot.rest.client.template;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.UpdateUserParameters;
import perobobbot.lang.Todo;
import perobobbot.rest.client.UserClient;
import perobobbot.rest.com.RestCredentialInfo;
import perobobbot.security.com.SimpleUser;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class RestTemplateUserClient implements UserClient {


    private final @NonNull AsyncRestOperations operations;

    @Override
    public @NonNull CompletionStage<ImmutableList<SimpleUser>> listAllUsers() {
        return operations.exchange("/users", HttpMethod.GET, null,
                                   new ParameterizedTypeReference<ImmutableList<SimpleUser>>() {
                                   })
                         .thenApply(HttpEntity::getBody);
//        })
//        return operations.getForObject("/users",ImmutableList.class)
//                         .thenApply(l -> (ImmutableList<SimpleUser>) l);
    }

    @Override
    public @NonNull CompletionStage<SimpleUser> createUser(@NonNull CreateUserParameters parameters) {
        return Todo.TODO();
    }

    @Override
    public @NonNull CompletionStage<SimpleUser> getUserByLogin(@NonNull String login) {
        return Todo.TODO();
    }

    @Override
    public @NonNull CompletionStage<SimpleUser> updateUser(@NonNull String login, @NonNull UpdateUserParameters parameters) {
        return Todo.TODO();
    }

    @Override
    public @NonNull CompletionStage<ImmutableList<RestCredentialInfo>> getUserCredentials(@NonNull String login) {
        return Todo.TODO();
    }
}
