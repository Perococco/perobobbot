package perobobbot.data.com;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class UserDTO {

    UUID id;

    String login;
}
