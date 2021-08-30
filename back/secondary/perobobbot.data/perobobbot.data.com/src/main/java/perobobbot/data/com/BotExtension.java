package perobobbot.data.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Bot;
import perobobbot.lang.TypeScript;

@Value
@TypeScript
public class BotExtension {

    @NonNull Bot bot;
    @NonNull Extension extension;
    boolean enabled;


}
