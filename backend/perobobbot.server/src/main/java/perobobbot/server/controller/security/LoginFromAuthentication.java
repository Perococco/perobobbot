package perobobbot.server.controller.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import perobobbot.common.lang.Lazy;

/**
 * @author Bastien Aracil
 * @version 19/04/2019
 */
@RequiredArgsConstructor
public class LoginFromAuthentication implements CharSequence {

    @NonNull
    private final Authentication authentication;

    private final Lazy<String> email = Lazy.basic(this::extractEmail);

    @NonNull
    private String extractEmail() {
        if (!authentication.isAuthenticated() || !(authentication.getPrincipal() instanceof UserDetails)) {
            throw new LoginFailed("Could not log user");
        }
        final UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        return userDetails.getUsername();
    }

    @Override
    public int length() {
        return email.get().length();
    }

    @Override
    public char charAt(int index) {
        return email.get().charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return email.get().subSequence(start,end);
    }

    @Override
    public String toString() {
        return email.get();
    }
}
