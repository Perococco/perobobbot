package perobobbot.frontfx.model.state;

import lombok.NonNull;
import lombok.Value;

import java.net.URI;

@Value
public class Configuration {

    @NonNull URI serverUri;
}
