package perobobbot.lang;

import lombok.NonNull;

import java.io.IOException;
import java.nio.file.Path;

public interface TemplateGenerator {
    @NonNull Path generate(@NonNull String groupId, @NonNull String artifactId) throws IOException;
}
