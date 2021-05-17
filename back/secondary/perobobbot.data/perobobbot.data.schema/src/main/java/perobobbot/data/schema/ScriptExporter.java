package perobobbot.data.schema;


import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author perococco
 */
@RequiredArgsConstructor
public class ScriptExporter {


    private static final Map<String, String> PERSISTENCE_UNIT_NAME_BY_ARGUMENT_KEY;
    private static final Map<String, SchemaExporter> SCRIPT_EXPORTER_BY_PERSISTENCE_UNIT_NAME;

    static {
        ImmutableMap.Builder<String, String> persistenceUnitName = ImmutableMap.builder();
        ImmutableMap.Builder<String, SchemaExporter> schemaExporterBuilder = ImmutableMap.builder();

        Stream.of("data")
              .forEach(p -> {
                  persistenceUnitName.put("--"+p,p);
                  schemaExporterBuilder.put(p,new SchemaExporter(p));
              });

        PERSISTENCE_UNIT_NAME_BY_ARGUMENT_KEY = persistenceUnitName.build();
        SCRIPT_EXPORTER_BY_PERSISTENCE_UNIT_NAME = schemaExporterBuilder.build();
    }

    public static void main(String[] args) throws Exception {
        final boolean debug = Arrays.asList(args).contains("--debug");

        try (Logger logger = (debug ? new NopLogger() : new Filelogger())) {
            new ScriptExporter(logger, args).export();
        }
    }


    private final Logger logger;

    private final String[] args;

    private Path rootPath = null;

    private List<ScriptExportInfo> scriptExportInfoList;

    private void export() throws Exception {
        logger.log("arguments : '%s'", String.join(" ", args));

        try {
            parseArguments(args);

            for (ScriptExportInfo scriptExportInfo : scriptExportInfoList) {
                final SchemaExporter schemaExporter = SCRIPT_EXPORTER_BY_PERSISTENCE_UNIT_NAME.get(scriptExportInfo.getPersistenceUnitName());
                if (schemaExporter == null) {
                    logger.log("[ERR] could not find exporter for persistenceUnit named '%s'",scriptExportInfo.getPersistenceUnitName());
                    continue;
                }
                logger.log("Export persistenceUnit='%s'",schemaExporter.getPersistenceUnitName());
                final Path exportResult = schemaExporter.export();
                final Path outputFile = rootPath.resolve(scriptExportInfo.getOutputPath());
                validateOutputFile(outputFile);
                Files.move(exportResult, outputFile);
            }
        } catch (Exception e) {
            logger.log(e);
            throw e;
        }
    }

    private void parseArguments(String[] args) throws IOException {
        final List<ScriptExportInfo> result = new ArrayList<>();

        for (String arg : args) {
            //loop over all arguments and keep only
            //with format  key=value
            int idx = arg.indexOf('=');
            if (idx < 0) {
                continue;
            }

            //retrieve key and value
            final String key = arg.substring(0, idx);
            final String value = arg.substring(idx + 1);

            if (key.equals("--project-dir")) {
                final Path valuePath = Path.of(value);
                final Path normalizedValuePath = valuePath.normalize();
                rootPath = normalizedValuePath.toAbsolutePath();
            } else {
                final String persistenceUnitName = PERSISTENCE_UNIT_NAME_BY_ARGUMENT_KEY.get(key);
                if (persistenceUnitName != null) {
                    result.add(parseScriptExportInfo(persistenceUnitName, value));
                }
            }
        }
        scriptExportInfoList = result;
        if (rootPath == null) {
            throw new RuntimeException("--project-dir is missing. Should provide the directory of the project");
        }
    }

    private ScriptExportInfo parseScriptExportInfo(@NonNull String persistenceUnitName, @NonNull String value) {
        final String[] tokens = value.split(",");
        final String moduleName;
        final int databaseVersion;
        switch (tokens.length) {
            case 1: {
                moduleName = persistenceUnitName;
                databaseVersion = Integer.parseInt(tokens[0]);
                break;
            }
            case 2: {
                moduleName = tokens[0];
                databaseVersion = Integer.parseInt(tokens[1]);
                break;
            }
            default:
                throw new RuntimeException("Could not parse value='" + value + "'. Must be of the form [<modulename>,]<database_version>");
        }
        return ScriptExportInfo.create(persistenceUnitName,moduleName,databaseVersion);
    }

    private static void validateOutputFile(Path file) throws IOException {
        if (!Files.isDirectory(file.getParent())) {
            throw new IllegalArgumentException("The SQL script export directory '" + file.getParent() + "' does not exist");
        }
        Files.deleteIfExists(file);
    }


}
