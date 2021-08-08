package perobobbot.security.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import perobobbot.lang.Lazy;
import perobobbot.security.com.LoginFailed;
import perobobbot.security.com.BotUser;

/**
 * @author perococco
 */
@RequiredArgsConstructor
public class LoginFromAuthentication implements CharSequence {

    @NonNull
    private final Authentication authentication;

    private final Lazy<String> login = Lazy.basic(this::extractLogin);

    @NonNull
    private String extractLogin() {
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof BotUser userNameProvider) {
            return userNameProvider.getUsername();
        }
        throw new LoginFailed("Could not log user");
    }

    @Override
    public int length() {
        return login.get().length();
    }

    @Override
    public char charAt(int index) {
        return login.get().charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return login.get().subSequence(start, end);
    }

    @Override
    public String toString() {
        return login.get();
    }
}
