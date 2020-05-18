package perobobbot.server;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import perobobbot.data.domain.DataDomainPackageMarker;
import perobobbot.data.jpa.repository.DataRepositoryMarker;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = {DataRepositoryMarker.class}
        )
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EntityScan(basePackageClasses = {DataDomainPackageMarker.class})
public class DataConfiguration {


}