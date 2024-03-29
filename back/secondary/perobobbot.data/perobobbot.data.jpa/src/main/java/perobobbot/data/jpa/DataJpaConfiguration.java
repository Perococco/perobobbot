package perobobbot.data.jpa;

import lombok.NonNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import perobobbot.data.domain.DataDomainPackageMarker;
import perobobbot.data.jpa.repository.DataRepositoryMarker;
import perobobbot.lang.Packages;

@Configuration
@EntityScan(basePackageClasses = {DataDomainPackageMarker.class})
@EnableJpaRepositories(
        basePackageClasses = {DataRepositoryMarker.class}
)
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class DataJpaConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with("Data JPA", DataJpaConfiguration.class);
    }

}
