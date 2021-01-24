package perobobbot.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

@RequiredArgsConstructor
public class AddSingletonToApplicationContext implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @NonNull
    private final String beanName;

    @NonNull
    private final Object singletonObject;

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        applicationContext.getBeanFactory().registerSingleton(beanName,singletonObject);
    }
}
