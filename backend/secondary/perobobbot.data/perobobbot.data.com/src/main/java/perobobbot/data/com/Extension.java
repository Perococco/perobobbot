package perobobbot.data.com;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.DTO;

@Value
@Builder
@DTO
@EqualsAndHashCode(of = "name")
public class Extension {

    @NonNull String name;

    boolean activated;

}
