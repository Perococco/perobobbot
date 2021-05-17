package perococco.perobobbot.common.lang;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Scope;

@Value
public class BasicScope implements Scope {

    @NonNull String name;
}
