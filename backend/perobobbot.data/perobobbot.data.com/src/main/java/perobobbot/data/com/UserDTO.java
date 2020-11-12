package perobobbot.data.com;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class UserDTO {

    @NonNull
    String login;
}
