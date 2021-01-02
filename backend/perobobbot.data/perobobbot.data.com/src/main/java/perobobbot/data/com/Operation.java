package perobobbot.data.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.DTO;

@Value
@DTO
public class Operation {

    @NonNull String name;
}
