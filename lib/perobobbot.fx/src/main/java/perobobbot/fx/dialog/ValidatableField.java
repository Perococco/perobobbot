package perobobbot.fx.dialog;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidatableField {

    String NO_VALUE = "#";

    /**
     * @return field name
     */
    String value() default NO_VALUE;
}
