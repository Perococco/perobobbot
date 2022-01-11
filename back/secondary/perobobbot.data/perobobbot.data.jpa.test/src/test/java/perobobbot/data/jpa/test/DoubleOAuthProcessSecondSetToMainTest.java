package perobobbot.data.jpa.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import perobobbot.lang.Platform;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class DoubleOAuthProcessSecondSetToMainTest extends JpaBaseTest {

    private OAuthHelper oAuthHelper;
    private UUID token1Id;
    private UUID token2Id;

    @BeforeEach
    public void setUpOAuth() throws ExecutionException, InterruptedException {
        this.oAuthHelper = OAuthHelper.builder(podam, clients)
                                      .addTwitchIdentity()
                                      .addTwitchIdentity()
                                      .build();

        this.oAuthHelper.mockOAuthManager(oAuthManager);

        final var token1 = oAuthService.createUserToken(this.userLogin, Platform.TWITCH).get();
        final var token2 = oAuthService.createUserToken(this.userLogin, Platform.TWITCH).get();

        this.token1Id = token1.getId();
        this.token2Id = token2.getId();

        oAuthService.setUserTokenAsMain(token2Id);


    }

    @Test
    public void token1ShouldExist() {
        final var token = oAuthService.findUserToken(token1Id);
        Assertions.assertTrue(token.isPresent());
    }

    @Test
    public void token2ShouldExist() {
        final var token = oAuthService.findUserToken(token2Id);
        Assertions.assertTrue(token.isPresent());
    }

    @Test
    public void token1ShouldNotBeMain() {
        final var token = oAuthService.getUserToken(token1Id);
        Assertions.assertFalse(token.isMain());
    }

    @Test
    public void token2ShouldBeMain() {
        final var token = oAuthService.getUserToken(token2Id);
        Assertions.assertTrue(token.isMain());
    }

}
