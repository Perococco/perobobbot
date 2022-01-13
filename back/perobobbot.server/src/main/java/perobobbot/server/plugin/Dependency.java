package perobobbot.server.plugin;

import lombok.NonNull;
import lombok.Value;

@Value
public class Dependency {

    @NonNull String groupId;
    @NonNull String artifactId;
    @NonNull String version;

    public @NonNull Dependency withVersion(@NonNull String newVersion) {
        if (newVersion.equals(version)) {
            return this;
        }
        return new Dependency(groupId,artifactId,newVersion);
    }
}
