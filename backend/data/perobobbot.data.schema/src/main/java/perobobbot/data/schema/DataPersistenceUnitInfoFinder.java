package perobobbot.data.schema;

import lombok.NonNull;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import perobobbot.data.domain.DataDomainPackageMarker;

import javax.persistence.spi.PersistenceUnitInfo;

public class DataPersistenceUnitInfoFinder {

    private final String persistenceUnitName;

    private final DefaultPersistenceUnitManager persistenceUnitManager;

    public DataPersistenceUnitInfoFinder(@NonNull String persistenceUnitName) {
        this.persistenceUnitName = persistenceUnitName;
        this.persistenceUnitManager = new DefaultPersistenceUnitManager();
        this.persistenceUnitManager.setDefaultPersistenceUnitName(persistenceUnitName);
        this.persistenceUnitManager.setPackagesToScan(DataDomainPackageMarker.class.getPackageName());
        this.persistenceUnitManager.preparePersistenceUnitInfos();
    }

    @NonNull
    public PersistenceUnitInfo find() {
        return persistenceUnitManager.obtainPersistenceUnitInfo(persistenceUnitName);
    }
}
