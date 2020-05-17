package perobobbot.data.service;

import lombok.NonNull;
import perobobbot.data.com.CreateUserParameter;
import perobobbot.data.com.UserDTO;

public interface UserService {

    @NonNull UserDTO getUserInfo(@NonNull String login);

    @NonNull UserDTO createUser(@NonNull CreateUserParameter parameter);

    @NonNull
    String getJWTToken(@NonNull String login);

}
