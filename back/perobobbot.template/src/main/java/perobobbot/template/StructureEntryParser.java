package perobobbot.template;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class StructureEntryParser {

    public static final String VM_SUFFIX = ".vm";

    public static final String COMMON_TYPE = "common";

    public static StructureEntry parse(@NonNull String line) {
        return new StructureEntryParser(line).parse();
    }

    private final @NonNull String line;

    private String[] tokens;
    private String type;
    private boolean velocityResource;
    private String resourceName;
    private String resourcePath;
    private String outputName;

    private @NonNull StructureEntry parse() {
        splitLineInTokens();
        extractType();
        extractResourceName();
        getIsVelocityResource();
        extractOrComputerOutputTemplateName();
        computeResourcePath();
        return new StructureEntry(type,velocityResource,resourcePath,outputName);
    }

    private void computeResourcePath() {
        final var name = resourceName.replaceAll("\\{groupId}","groupId");
        resourcePath = "/template/type/"+type+"/"+name;
    }

    private void splitLineInTokens() {
        this.tokens = line.split(" +");
    }

    private RuntimeException createException(String message) {
        return new IllegalArgumentException("Cannot parse type from line '" + line + "': " + message);
    }

    private void extractType() {
        if (tokens.length == 0 || !tokens[0].startsWith(":")) {
            throw createException("Type not defined correctly");
        }
        type = tokens[0].substring(1);
    }

    private void extractResourceName() {
        if (tokens.length<2) {
            throw createException("No resource defined");
        }
        resourceName = tokens[1];
    }

    private void getIsVelocityResource() {
        velocityResource = resourceName.endsWith(VM_SUFFIX);
    }

    private void extractOrComputerOutputTemplateName() {
        if (tokens.length >= 3) {
            outputName = tokens[2];
        }
        else if (velocityResource) {
            outputName = resourceName.substring(0, resourceName.length() - VM_SUFFIX.length());
        } else {
            outputName = resourceName;
        }
    }

}
