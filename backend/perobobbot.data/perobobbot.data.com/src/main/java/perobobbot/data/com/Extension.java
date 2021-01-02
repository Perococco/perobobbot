package perobobbot.data.com;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.DTO;

@Value
@Builder
@DTO
public class Extension {

    @NonNull String name;

    @NonNull boolean enabled;

}
