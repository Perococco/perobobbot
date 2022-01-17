package perobobbot.data.jpa.test;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import perobobbot.data.com.CreateClientParameter;
import perobobbot.data.com.CreateUserParameters;
import perobobbot.data.jpa.DataJpaConfiguration;
import perobobbot.data.jpa.repository.PlatformUserRepository;
import perobobbot.data.jpa.repository.RepositoryToolConfiguration;
import perobobbot.data.jpa.test.component.TestConfiguration;
import perobobbot.data.jpa.test.component.TestPackageMarker;
import perobobbot.data.service.ClientService;
import perobobbot.data.service.OAuthService;
import perobobbot.data.service.PlatformUserService;
import perobobbot.data.service.UserService;
import perobobbot.lang.client.DecryptedClient;
import perobobbot.lang.Platform;
import perobobbot.lang.RandomString;
import perobobbot.lang.Secret;
import perobobbot.oauth.OAuthManager;
import perobobbot.oauth.Token;
import perobobbot.oauth.UserOAuthInfo;
import perobobbot.security.com.Authentication;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import java.net.URI;
import java.util.concurrent.CompletableFuture;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@ComponentScan(basePackageClasses = {DataJpaConfiguration.class, TestPackageMarker.class})
@ContextConfiguration(classes = {DataJpaConfiguration.class, RepositoryToolConfiguration.class, TestConfiguration.class})
public abstract class JpaBaseTest {

    protected @MockBean
    OAuthManager oAuthManager;

    protected PodamFactory podam = new PodamFactoryImpl();

    @Autowired
    protected ClientService clientService;
    @Autowired
    protected UserService userService;
    @Autowired
    protected OAuthService oAuthService;
    @Autowired
    protected PlatformUserService platformUserService;

    @Autowired
    protected PlatformUserRepository platformUserRepository;

    protected boolean debug = false;

    protected DecryptedClient twitchClient;
    protected DecryptedClient discordClient;
    protected String userLogin;

    protected ImmutableMap<Platform, DecryptedClient> clients;

    @BeforeEach
    public void setUp() {



        this.twitchClient = clientService.createClient(new CreateClientParameter(Platform.TWITCH, "twitch_id", Secret.with("twitch_secret")));
        this.discordClient = clientService.createClient(new CreateClientParameter(Platform.DISCORD, "discord_id", Secret.with("discord_secret")));


        Mockito.when(oAuthManager.getClientToken(twitchClient)).thenReturn(CompletableFuture.completedFuture(podam.manufacturePojo(Token.class)));
        Mockito.when(oAuthManager.getClientToken(discordClient)).thenReturn(CompletableFuture.completedFuture(podam.manufacturePojo(Token.class)));



        this.clients = ImmutableMap.of(Platform.TWITCH, twitchClient, Platform.DISCORD, discordClient);

        this.userLogin = RandomString.createWithLength(12);

        userService.createUser(new CreateUserParameters(this.userLogin, Authentication.password("password")));

        oAuthService.authenticateClient(Platform.TWITCH);
        oAuthService.authenticateClient(Platform.DISCORD);

        debug = false;
    }


    @AfterEach
    public void tearDown() throws InterruptedException {
        if (debug) {
            Thread.sleep(10_000*60);
        }
    }

    public @NonNull <T> UserOAuthInfo<T> createUserOAuthInfoForTest(@NonNull T value) {
        return new UserOAuthInfo<>(URI.create("https://localhost"), CompletableFuture.completedFuture(value));
    }


}
