package perococco.perobobbot.rest.client.template;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.Delegate;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import perobobbot.rest.client.*;
import perobobbot.security.com.Credential;
import perobobbot.security.com.JwtInfo;
import perobobbot.security.com.SimpleUser;

import java.io.IOException;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicReference;

public class PeroRestTemplateClientManager implements ClientManager {

    private final AtomicReference<JwtInfo> token = new AtomicReference<>(null);

    @Delegate(types = {RestOperations.class})
    private final RestTemplate restTemplate;

    private @Getter final SecurityClient securityClient;
    private @Getter final BotClient botClient;
    private @Getter final CredentialClient credentialClient;
    private @Getter final ExtensionClient extensionClient;
    private @Getter final I18nClient i18nClient;
    private @Getter final UserClient userClient;


    public PeroRestTemplateClientManager(@NonNull String baseUrl) {
        this.restTemplate = setupRestTemplate(baseUrl);
        this.restTemplate.getInterceptors().add(this::addJwTokenToHeader);
        final var operations = new AsyncRestOperationWrapper(restTemplate);
        this.securityClient = new RestTemplateSecurityClient(operations);
        this.botClient = new RestTemplateBotClient(operations);
        this.credentialClient = new RestTemplateCredentialClient(operations);
        this.extensionClient = new RestTemplateExtensionClient(operations);
        this.i18nClient = new RestTemplateI18nClient(operations);
        this.userClient = new RestTemplateUserClient(operations);
    }

    public @NonNull CompletionStage<SimpleUser> login(@NonNull Credential credential) {
        return securityClient().signIn(credential)
                               .whenComplete((r, t) -> this.token.set(r))
                               .thenApply(JwtInfo::getUser)
                .whenComplete((r,t) -> {
                    if (t !=null) {
                        t.printStackTrace();
                    }
                });
    }

    public void logout() {
        this.token.set(null);
    }


    private ClientHttpResponse addJwTokenToHeader(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        final var t = this.token.get();
        if (t != null) {
            request.getHeaders().add("Authorization", "bearer " + t.getToken());
        }
        return execution.execute(request, body);
    }

    private static RestTemplate setupRestTemplate(@NonNull String apiBaseUrl) {
        final var mapper = new ObjectMapper().registerModules(new GuavaModule(), new JavaTimeModule(), new Jdk8Module());

        final var restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(apiBaseUrl));
        restTemplate.getMessageConverters().removeIf(i -> i instanceof MappingJackson2HttpMessageConverter);
        restTemplate.getMessageConverters().add(0, new MappingJackson2HttpMessageConverter(mapper));
        return restTemplate;
    }
}
