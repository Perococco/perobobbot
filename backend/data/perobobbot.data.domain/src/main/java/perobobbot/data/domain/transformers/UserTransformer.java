package perobobbot.data.domain.transformers;

import lombok.NonNull;
import perobobbot.data.com.UserDTO;
import perobobbot.data.domain.User;
import perobobbot.lang.Transformer;

public class UserTransformer implements Transformer<User, UserDTO> {

    private static final UserTransformer INSTANCE = new UserTransformer();

    @NonNull
    public static UserTransformer create() {
        return INSTANCE;
    }

    @NonNull
    @Override
    public UserDTO transform(@NonNull User input) {
        return UserDTO.builder()
                      .login(input.getLogin())
                      .build();
    }
}
