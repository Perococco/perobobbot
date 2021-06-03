package perobobbot.rest.controller.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
public class RestPointArchitecture {

    @Pointcut("execution(* perobobbot.rest.controller.*Controller.*(..))")
    public void allRestCalls() {}
}
