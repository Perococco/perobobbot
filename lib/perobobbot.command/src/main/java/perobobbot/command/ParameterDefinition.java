package perobobbot.command;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

@Value
@EqualsAndHashCode(of = "name")
public class ParameterDefinition {

    @NonNull String name;

    boolean optional;
}
