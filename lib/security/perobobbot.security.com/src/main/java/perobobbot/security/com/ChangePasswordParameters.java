package perobobbot.security.com;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.TypeScript;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
@TypeScript
public class ChangePasswordParameters {
    @NotBlank
    @NonNull String password;
    @NotBlank
    @Size(min = 7)
    @NonNull String newPassword;
}
