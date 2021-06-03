package perobobbot.oauth;

import java.lang.annotation.*;

@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequiredScope {

    String value();

    boolean optional() default false;
}
