package perobobbot.data.jpa;

import lombok.NonNull;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import perobobbot.data.domain.DataDomainPackageMarker;
import perobobbot.data.jpa.repository.DataRepositoryMarker;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;

@Configuration
@EntityScan(basePackageClasses = {DataDomainPackageMarker.class})
@EnableJpaRepositories(
        basePackageClasses = {DataRepositoryMarker.class}
)
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class JpaConfiguration {

    public static @NonNull Plugin provider() {
        return Plugin.with(PluginType.SECONDARY,"Data", JpaConfiguration.class);
    }

}
