package perobobbot.template;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

import java.net.URL;

@Value
public class StructureEntry {

    public static final String VM_SUFFIX = ".vm";

    @NonNull String type;

    boolean velocityResource;

    @NonNull String resourcePath;

    @NonNull String templatePath;


    public @NonNull String prepareTarget(@NonNull String groupId) {
        String result = templatePath;
        if (result.contains("{groupId}")) {
            result = result.replaceAll("\\{groupId}", groupId.replaceAll("\\.", "/"));
        }
        return result;
    }


    public @NonNull URL getResource() {
        return Templates.class.getResource(resourcePath);
    }

    public boolean isIncludedFor(String type) {
        return this.type.equalsIgnoreCase("common") || this.type.equalsIgnoreCase(type);
    }
}
