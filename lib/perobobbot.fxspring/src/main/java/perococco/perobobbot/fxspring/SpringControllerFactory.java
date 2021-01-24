package perococco.perobobbot.fxspring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import perobobbot.fx.ControllerFactory;

@RequiredArgsConstructor
@Log4j2
public class SpringControllerFactory implements ControllerFactory {

    @NonNull
    private final BeanFactory beanFactory;

    @Override
    public @NonNull Object getController(@NonNull Class<?> controllerType) throws Exception {
        try {
            return beanFactory.getBean(controllerType);
        } catch (NoSuchBeanDefinitionException e) {
            try {
                return controllerType.getConstructor().newInstance();
            } catch (NoSuchMethodException nme) {
                throw new IllegalArgumentException("It seems that the controller "+controllerType+" should be a Spring bean. Forgot the @FXMLComponent annotation ?",nme);
            }
        }
    }
}
