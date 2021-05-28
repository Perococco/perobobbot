package perobobbot.rest.controller.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public interface RestPointArchitecture {

    @Pointcut("execution(* perobobbot.rest.controller.*Controller(..))")
    void allRestCall();
}
