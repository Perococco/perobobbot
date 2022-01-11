package perobobbot.data.jpa.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import perobobbot.lang.Platform;
import perobobbot.lang.TwitchIdentity;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class SingleOAuthProcessTest extends JpaBaseTest {

    private OAuthHelper oAuthHelper;
    private UUID tokenId;

    @BeforeEach
    public void setUpOAuth() throws ExecutionException, InterruptedException {
        this.oAuthHelper = OAuthHelper.builder(podam, clients).addTwitchIdentity().build();

        this.oAuthHelper.mockOAuthManager(oAuthManager);

        final var token = oAuthService.createUserToken(this.userLogin, Platform.TWITCH).get();
        this.tokenId = token.getId();
    }

    @Test
    public void tokenShouldExist() {
        final var token = oAuthService.findUserToken(tokenId);
        Assertions.assertTrue(token.isPresent());
    }

    @Test
    public void tokenShouldBeMain() {
        final var token = oAuthService.getUserToken(tokenId);
        Assertions.assertTrue(token.isMain());
    }

    @Test
    public void tokenShouldHaveRightOwner() {
        final var token = oAuthService.getUserToken(tokenId);
        Assertions.assertEquals(this.userLogin,token.getOwnerLogin());
    }

    @Test
    public void tokenShouldHaveRightPlatform() {
        final var token = oAuthService.getUserToken(tokenId);
        Assertions.assertEquals(Platform.TWITCH, token.getPlatform());
    }

    @Test
    public void tokenPlatformUserShouldHaveRightUserId() {
        final var platformUser = oAuthService.getUserToken(tokenId).getPlatformUser();
        final var userIdentity = oAuthHelper.getIdentity(Platform.TWITCH,0);
        Assertions.assertEquals(userIdentity.getUserId(), platformUser.getUserId());
    }

    @Test
    public void tokenPlatformUserShouldHaveRightLogin() {
        final var platformUser = oAuthService.getUserToken(tokenId).getPlatformUser();
        final var userIdentity = oAuthHelper.getIdentity(Platform.TWITCH,0);
        Assertions.assertEquals(userIdentity.getLogin(), platformUser.getLogin());
    }

    @Test
    public void tokenPlatformUserShouldHaveRightPseudo() {
        final var platformUser = oAuthService.getUserToken(tokenId).getPlatformUser();
        final var userIdentity = (TwitchIdentity)oAuthHelper.getIdentity(Platform.TWITCH,0);
        Assertions.assertEquals(userIdentity.getPseudo(), platformUser.getPseudo());
    }
}
