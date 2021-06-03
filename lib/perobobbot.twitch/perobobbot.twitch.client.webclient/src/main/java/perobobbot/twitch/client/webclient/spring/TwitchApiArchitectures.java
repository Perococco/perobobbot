package perobobbot.twitch.client.webclient.spring;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class TwitchApiArchitectures {

    @Pointcut("execution(* perobobbot.twitch.client.webclient.WebClientAppTwitchService.*(..))")
    public void allCallsToTwitchApi() {}

}
