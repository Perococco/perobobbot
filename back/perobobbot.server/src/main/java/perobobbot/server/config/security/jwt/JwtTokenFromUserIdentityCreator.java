package perobobbot.server.config.security.jwt;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.BadCredentialsException;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.service.UserService;
import perobobbot.lang.Platform;
import perobobbot.lang.fp.Function1;
import perobobbot.security.com.Authentication;
import perobobbot.security.com.JwtInfo;
import perobobbot.security.com.User;
import perobobbot.security.core.jwt.JWTokenManager;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtTokenFromUserIdentityCreator {


    public static @NonNull JwtInfo create(@NonNull JWTokenManager jwTokenManager, @NonNull UserService userService, @NonNull String login, @NonNull Platform platform) {
        return new JwtTokenFromUserIdentityCreator(jwTokenManager, userService, login, platform).create();
    }

    public static @NonNull Function1<String,JwtInfo> forLogin(@NonNull JWTokenManager jwTokenManager, @NonNull UserService userService, @NonNull Platform platform) {
        return (login) -> create(jwTokenManager, userService, login, platform);
    }

    private final @NonNull JWTokenManager jwTokenManager;
    private final @NonNull UserService userService;
    private final @NonNull String login;
    private final @NonNull Platform platform;

    private User user = null;

    private @NonNull JwtInfo create() {
        this.retrieveUserFromUserIdentity();
        if (userAlreadyExists()) {
            checkUsedOpenIdMatchesWithUserOpenId();
        } else {
            createNewUser();
        }
        return jwTokenManager.createJwtInfo(login);
    }

    private void retrieveUserFromUserIdentity() {
        this.user = userService.findUser(login).orElse(null);
    }

    private boolean userAlreadyExists() {
        return this.user != null;
    }

    private void checkUsedOpenIdMatchesWithUserOpenId() {
        assert user!=null;
        final var userPlatform = user.getIdentificationOpenIdPlatform().orElse(null);
        if (platform.equals(userPlatform)) {
            return;
        }
        LOG.warn("Invalid platform used to identify user '{}': expected='{}' actual='{}'", login, userPlatform==null?"None":userPlatform.name(), platform);
        throw new BadCredentialsException("A user exists with this login already");
    }

    private void createNewUser() {
        final var parameter = new CreateUserParameters(login, Authentication.openId(platform));
        userService.createUser(parameter);

    }
}
