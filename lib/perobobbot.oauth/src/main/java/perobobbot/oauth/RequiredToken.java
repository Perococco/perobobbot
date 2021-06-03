package perobobbot.oauth;

import perobobbot.lang.TokenType;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredToken {

    TokenType value();

}
