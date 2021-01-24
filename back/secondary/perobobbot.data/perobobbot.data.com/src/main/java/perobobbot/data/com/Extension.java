package perobobbot.data.com;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;

@Value
@Builder
@TypeScript
@EqualsAndHashCode(of = "name")
public class Extension {

    @NonNull String name;

    boolean activated;

}
