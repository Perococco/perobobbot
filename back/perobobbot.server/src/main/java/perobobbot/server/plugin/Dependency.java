package perobobbot.server.plugin;

import lombok.NonNull;
import lombok.Value;

@Value
public class Dependency {

    @NonNull String groupId;
    @NonNull String artifactId;
    @NonNull String version;

}
