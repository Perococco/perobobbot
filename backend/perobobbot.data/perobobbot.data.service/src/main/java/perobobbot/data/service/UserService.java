package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.com.UserDTO;

public interface UserService {

    @NonNull UserDTO getUserInfo(@NonNull String login);

    @NonNull UserDTO createUser(@NonNull CreateUserParameters parameters);

    @NonNull
    String getJWTToken(@NonNull String login);

}
