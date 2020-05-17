package perobobbot.data.jpa;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.config.BootstrapMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import perobobbot.common.lang.Packages;
import perobobbot.data.domain.DataDomainPackageMarker;
import perobobbot.data.jpa.repository.DataRepositoryMarker;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = {DataRepositoryMarker.class},
        bootstrapMode = BootstrapMode.LAZY
)
@EnableTransactionManagement
@EntityScan(basePackageClasses = {DataDomainPackageMarker.class})
public class DataConfiguration {

    public static Packages provider() {
        return Packages.with("DATA_JPA",
                             DataConfiguration.class);
    }


}
