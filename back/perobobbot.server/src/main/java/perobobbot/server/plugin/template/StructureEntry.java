package perobobbot.server.plugin.template;

import lombok.NonNull;
import lombok.Value;

@Value
public class StructureEntry {

    public static final String VM_SUFFIX = ".vm";

    @NonNull String resourcePath;

    @NonNull String templatePath;


    public boolean isVelocityResource() {
        return resourcePath.endsWith(VM_SUFFIX);
    }

    public @NonNull String prepareTarget(@NonNull String groupId) {
        String result = templatePath;
        if (templatePath.endsWith(VM_SUFFIX)) {
            result = templatePath.substring(0, templatePath.length() - VM_SUFFIX.length());
        }
        if (result.contains("groupId")) {
            result = result.replaceAll("groupId", groupId.replaceAll("\\.", "/"));
        }
        return result;
    }


    public static StructureEntry parse(@NonNull String line) {
        final var tokens = line.split(" +");
        if (tokens.length == 0) {
            throw new IllegalArgumentException("Cannot parse line '"+line+"' to StructureEntry");
        }
        final var templateFileName = (tokens.length>=2)?tokens[1]:tokens[0];
        return new StructureEntry(tokens[0],templateFileName);
    }

    private static @NonNull String extractResourceBaseName(@NonNull String path) {
        final var idxLastSlash = path.lastIndexOf("/");
        if (idxLastSlash < 0) {
            return "";
        }
        return path.substring(0,idxLastSlash);
    }

    private static @NonNull String extractResourceFileName(@NonNull String path) {
        final var idxLastSlash = path.lastIndexOf("/");
        if (idxLastSlash < 0) {
            return path;
        }
        return path.substring(idxLastSlash+1);
    }


}
