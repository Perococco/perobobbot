package perobobbot.data.schema;

import lombok.NonNull;
import lombok.Value;

import java.io.File;
import java.nio.file.Path;

@Value
public class ScriptExportInfo {

    @NonNull
    public static ScriptExportInfo create(@NonNull String persistenceUnitName, int databaseVersion) {
        return create(persistenceUnitName,persistenceUnitName,databaseVersion);
    }

    @NonNull
    public static ScriptExportInfo create(@NonNull String persistenceUnitName, @NonNull String moduleName, int databaseVersion) {
        return new ScriptExportInfo(
                persistenceUnitName,
                moduleName,
                databaseVersion,
                getPathToExportScript(moduleName,databaseVersion)
        );
    }

    @NonNull String persistenceUnitName;

    String packageName;

    int databaseVersion;

    @NonNull Path outputPath;

    @NonNull
    private static Path getPathToExportScript(@NonNull String moduleName, int databaseVersion) {
        final String format = getFormatForExportScriptPath();
        return Path.of(String.format(format, moduleName, databaseVersion));
    }

    @NonNull
    private static String getFormatForExportScriptPath() {
        final String format = "export/create_database_%1$s_%2$d.sql";
        if ("/".equals(File.separator)) {
            return format;
        }
        return format.replaceAll("/", File.pathSeparator);
    }
}
