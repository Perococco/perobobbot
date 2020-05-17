package perobobbot.server;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import perobobbot.data.domain.DomainPackageMarker;

@Configuration
@EntityScan(basePackageClasses = DomainPackageMarker.class)
public class DomainConfiguration {
}
