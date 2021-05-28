package perobobbot.server.test;

import lombok.Getter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigurationExcludeFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import perobobbot.data.event.DataEventConfiguration;
import perobobbot.data.jpa.DataJpaConfiguration;
import perobobbot.data.jpa.repository.UserRepository;
import perobobbot.data.security.DataSecurityConfiguration;
import perobobbot.data.service.SecuredService;
import perobobbot.data.service.UserService;

/**
 * @author Bastien Aracil
 */
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@ComponentScan(
        excludeFilters = {@ComponentScan.Filter(
                type = FilterType.CUSTOM,
                classes = {TypeExcludeFilter.class}
        ), @ComponentScan.Filter(
                type = FilterType.CUSTOM,
                classes = {AutoConfigurationExcludeFilter.class}
        )}
)
//@AutoConfigureDataJpa
//@EnableAspectJAutoProxy
@ContextConfiguration(classes = {
        DataJpaConfiguration.class,
        DataSecurityConfiguration.class,
        DataEventConfiguration.class,

})
public abstract class AbstractDataJPATest {


    @Autowired
    @SecuredService
    @Getter
    private UserRepository userRepository;

    @Autowired
    @SecuredService
    @Getter
    private UserService userService;


    @Qualifier("data")
    @Autowired
    @Getter
    private JpaTransactionManager transactionManager;

}
