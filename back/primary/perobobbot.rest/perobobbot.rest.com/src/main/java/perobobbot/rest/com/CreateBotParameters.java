package perobobbot.rest.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;

@Value
@TypeScript
public class CreateBotParameters {

    @NonNull String name;
}
