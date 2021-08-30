package perobobbot.data.com;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;

import java.util.UUID;

@Value
@Builder
@TypeScript
@EqualsAndHashCode(of = "name")
public class Extension {

    @NonNull UUID id;

    @NonNull String name;

    boolean activated;

    boolean available;

}
