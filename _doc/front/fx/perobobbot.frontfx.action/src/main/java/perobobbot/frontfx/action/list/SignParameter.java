package perobobbot.frontfx.action.list;

import lombok.NonNull;
import lombok.Value;
import perobobbot.lang.Secret;
import perobobbot.security.com.Credential;
import perobobbot.validation.ErrorType;
import perobobbot.validation.Validatable;
import perobobbot.validation.Validation;

@Value
public class SignParameter implements Validatable {

    public static final String LOGIN_FIELD = "login";
    public static final String SECRET_FIELD = "secret";

    @NonNull String login;
    @NonNull Secret password;

    public SignParameter(@NonNull String login, @NonNull Secret password) {
        this.login = login;
        this.password = password;
    }

    public SignParameter(@NonNull String login, @NonNull String password) {
        this(login,Secret.with(password));
    }

    public @NonNull Credential toCredential() {
        return new Credential(login,password.getValue());
    }

    @Override
    public @NonNull Validation validate(@NonNull Validation validation) {
        validation.with(LOGIN_FIELD,login).isNotBlank();
        validation.with(SECRET_FIELD,password).errorIf(Secret::isBlank, ErrorType.NOT_BLANK_TEXT_REQUIRED);
        return validation;
    }
}
