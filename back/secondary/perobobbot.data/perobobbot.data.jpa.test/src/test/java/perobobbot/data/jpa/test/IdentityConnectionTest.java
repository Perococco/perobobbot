package perobobbot.data.jpa.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import perobobbot.lang.Platform;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class IdentityConnectionTest extends JpaBaseTest {

    private OAuthHelper oAuthHelper;


    private UUID twitchTokenId;
    private UUID discordTokenId;

    @BeforeEach
    public void createConnection() throws ExecutionException, InterruptedException {
        this.oAuthHelper = OAuthHelper.builder(podam,clients).addDefaultIdentities().build();

        this.oAuthHelper.mockOAuthManager(oAuthManager);

        this.twitchTokenId = oAuthService.createUserToken(this.userLogin, Platform.TWITCH).get().getId();
        this.discordTokenId = oAuthService.createUserToken(this.userLogin, Platform.DISCORD).get().getId();
    }

    @Test
    public void twitchPlatformUserShouldHaveAToken() {
        final var twitchIdentity = oAuthHelper.getIdentity(Platform.TWITCH,0);
        final var twitchUser = platformUserRepository.getByPlatformAndUserId(Platform.TWITCH, twitchIdentity.getUserId());
        Assertions.assertTrue(twitchUser.getUserToken().isPresent());
    }

    @Test
    public void discordPlatformUserShouldHaveAToken() {
        final var discordIdentity = oAuthHelper.getIdentity(Platform.DISCORD,0);
        final var discordUser = platformUserRepository.getByPlatformAndUserId(Platform.DISCORD, discordIdentity.getUserId());
        Assertions.assertTrue(discordUser.getUserToken().isPresent());
    }

    @Test
    public void platformUsersShouldHaveSameTokenOwner() {
        final var twitchIdentity = oAuthHelper.getIdentity(Platform.TWITCH,0);
        final var discordIdentity = oAuthHelper.getIdentity(Platform.DISCORD,0);

        final var twitchUser = platformUserRepository.getByPlatformAndUserId(Platform.TWITCH, twitchIdentity.getUserId());
        final var discordUser = platformUserRepository.getByPlatformAndUserId(Platform.DISCORD, discordIdentity.getUserId());


        final var twitchTokenOwner = twitchUser.getTokenOwnerLogin().get();
        final var discordTokenOwner = discordUser.getTokenOwnerLogin().get();


        Assertions.assertEquals(twitchTokenOwner, discordTokenOwner);
    }
}
