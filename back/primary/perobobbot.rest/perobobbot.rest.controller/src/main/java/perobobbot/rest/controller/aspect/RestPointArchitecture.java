package perobobbot.rest.controller.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.access.method.P;

@Aspect
public interface RestPointArchitecture {

    @Pointcut("execution(* perobobbot.rest.controller.*Controller(..))")
    void allRestCall();
}
