package perobobbot.rest.client;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.UpdateUserParameters;
import perobobbot.rest.com.RestCredentialInfo;
import perobobbot.security.com.SimpleUser;

public interface UserClient {

    @NonNull ImmutableList<SimpleUser> listAllUsers();

    @NonNull SimpleUser createUser(@NonNull CreateUserParameters parameters);

    @NonNull SimpleUser getUserByLogin(@NonNull String login);

    @NonNull SimpleUser updateUser(@NonNull String login, @NonNull UpdateUserParameters parameters);

    @NonNull ImmutableList<RestCredentialInfo> getUserCredentials(@NonNull String login);

}
