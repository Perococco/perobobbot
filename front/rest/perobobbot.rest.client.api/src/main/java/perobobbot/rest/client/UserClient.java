package perobobbot.rest.client;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.UpdateUserParameters;
import perobobbot.rest.com.RestCredentialInfo;
import perobobbot.security.com.SimpleUser;

import java.util.concurrent.CompletionStage;

public interface UserClient {

    @NonNull CompletionStage<ImmutableList<SimpleUser>> listAllUsers();

    @NonNull CompletionStage<SimpleUser> createUser(@NonNull CreateUserParameters parameters);

    @NonNull CompletionStage<SimpleUser> getUserByLogin(@NonNull String login);

    @NonNull CompletionStage<SimpleUser> updateUser(@NonNull String login, @NonNull UpdateUserParameters parameters);

    @NonNull CompletionStage<ImmutableList<RestCredentialInfo>> getUserCredentials(@NonNull String login);

}
