package perococco.perobobbot.frontfx.gui.fxml;

import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to use on FX controller.
 * It defines the marker controller as a component with scope "prototype"
 */
@Component
@Scope(AbstractBeanFactory.SCOPE_PROTOTYPE)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FXMLController {
}
