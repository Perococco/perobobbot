package perobobbot.data.domain.transformers;

import lombok.NonNull;
import perobobbot.data.com.UserDTO;
import perobobbot.data.domain.User;

/**
 * @author Perococco
 */
public class Transformers {

    @NonNull
    public static UserDTO toDTO(@NonNull User user) {
        return UserTransformer.create().transform(user);
    }

}
