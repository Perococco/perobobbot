package perobobbot.security.core;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import perobobbot.lang.Lazy;
import perobobbot.security.com.LoginFailed;

/**
 * @author Perococco
 */
@RequiredArgsConstructor
public class LoginFromAuthentication implements CharSequence {

    @NonNull
    private final Authentication authentication;

    private final Lazy<String> login = Lazy.basic(this::extractLogin);

    @NonNull
    private String extractLogin() {
        if (!authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new LoginFailed("Could not log user");
        }
        final UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        return userDetails.getUsername();
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
