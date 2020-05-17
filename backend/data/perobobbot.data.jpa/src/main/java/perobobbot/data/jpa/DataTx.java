package perobobbot.data.jpa;

import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Transactional("data")
public @interface DataTx {

    @AliasFor(annotation = Transactional.class,attribute = "propagation")
    Propagation propagation() default Propagation.REQUIRED;

    @AliasFor(annotation = Transactional.class,attribute = "readOnly")
    boolean readOnly() default false;

    @AliasFor(annotation = Transactional.class,attribute = "rollbackFor")
    Class<? extends Throwable>[] rollbackFor() default {Throwable.class};

    @AliasFor(annotation = Transactional.class,attribute = "noRollbackFor")
    Class<? extends Throwable>[] noRollbackFor() default {};

}
