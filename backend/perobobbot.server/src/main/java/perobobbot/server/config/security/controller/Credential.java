package perobobbot.server.config.security.controller;

import lombok.Value;
import perobobbot.lang.DTO;

import javax.validation.constraints.NotBlank;

/**
 * @author Perococco
 * @version 15/04/2019
 */
@Value
@DTO
public class Credential {

    @NotBlank String login;

    @NotBlank String password;

}
