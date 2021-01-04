package perobobbot.rest.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.DTO;

@Value
@DTO
public class CreateBotParameters {

    @NonNull String name;
}
