package perobobbot.data.schema;

import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;

import javax.persistence.spi.PersistenceUnitInfo;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Properties;

/**
 * @author Perococco
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SchemaExporter {


    @NonNull
    @Getter
    private final String persistenceUnitName;

    @NonNull
    private final Properties exportHibernateProperties;

    private Path outputFile;

    private ImmutableSet<String> persistedClassNames;

    private ServiceRegistry serviceRegistry;

    private Metadata metadata;

    private Path temporaryFile;

    private String defaultSchemaName;

    public SchemaExporter(@NonNull String persistenceUnitName) {
        this(persistenceUnitName,HibernateProperties.get());
    }

    @NonNull
    public Path export() throws IOException {
        try {
            this.prepareOutputFile();
            this.fetchPersistedClassNames();
            this.createServiceRegistry();
            this.createMetaData();
            this.prepareTemporaryFile();
            this.exportSchemaToTemporaryFile();
            this.fetchDefaultSchemaName();
            this.createFinalSchemaFile();
            return outputFile;
        } finally {
            this.destroyServiceRegistry();
        }
    }

    private void prepareOutputFile() throws IOException {
        this.outputFile = Files.createTempFile("export", "sql");
    }

    private void fetchPersistedClassNames() {
        final PersistenceUnitInfo info = new DataPersistenceUnitInfoFinder(persistenceUnitName).find();
        this.persistedClassNames = ImmutableSet.copyOf(info.getManagedClassNames());
    }

    private void createServiceRegistry() {
        this.serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(exportHibernateProperties)
                .build();
    }


    private void createMetaData() {
        final MetadataSources metadataSources = new MetadataSources(serviceRegistry);
        persistedClassNames.forEach(metadataSources::addAnnotatedClassName);
        this.metadata = metadataSources.buildMetadata();
    }

    private void prepareTemporaryFile() throws IOException {
        temporaryFile = Files.createTempFile("export", ".sql");
    }

    private void exportSchemaToTemporaryFile() {
        new SchemaExport()
                .setFormat(true)
                .setDelimiter(";")
                .setOutputFile(temporaryFile.toAbsolutePath().toString())
                .execute(EnumSet.of(TargetType.SCRIPT), SchemaExport.Action.CREATE, metadata);
    }

    private void fetchDefaultSchemaName() {
        this.defaultSchemaName = exportHibernateProperties.getProperty("hibernate.default_schema");
    }

    private void createFinalSchemaFile() throws IOException {
        try (PrintStream ps = new PrintStream(Files.newOutputStream(outputFile, StandardOpenOption.CREATE))) {
            ps.printf("drop schema if exists %s;%n", defaultSchemaName);
            ps.printf("create schema if not exists %s;%n", defaultSchemaName);
            ps.println();
            Files.copy(temporaryFile, ps);
        }
    }

    private void destroyServiceRegistry() {
        Optional.ofNullable(serviceRegistry).ifPresent(StandardServiceRegistryBuilder::destroy);
    }

}
