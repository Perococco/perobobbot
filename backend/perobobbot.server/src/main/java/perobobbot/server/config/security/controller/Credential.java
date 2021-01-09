package perobobbot.server.config.security.controller;

import lombok.Value;
import perobobbot.lang.TypeScript;

import javax.validation.constraints.NotBlank;

/**
 * @author Perococco
 * @version 15/04/2019
 */
@Value
@TypeScript
public class Credential {

    @NotBlank String login;

    @NotBlank String password;

}
