package perobobbot.server.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.http.WebHookDispatcher;
import perobobbot.server.config.externaluri.ExternalURI;

import java.net.URI;

@Configuration
@RequiredArgsConstructor
public class WebHookConfiguration {

    public static final String WEBHOOK_PATH = "/webhook";

    @Qualifier("webhook")
    private final @NonNull ExternalURI webHookExternalURI;
    @Qualifier("oauth")
    private final @NonNull ExternalURI oauthExternalURI;
    @Value("${server.servlet.context-path}")
    private final String serverContextPath;


    @Bean
    public @NonNull WebHookDispatcher webHookDispatcher() {
        var webHookURI = formFullURI(webHookExternalURI);
        var oauthURI = formFullURI(oauthExternalURI);
        return WebHookDispatcher.create(webHookURI, oauthURI);
    }

    @Bean
    public @NonNull ServletRegistrationBean<?> webhookServlet() {
        final var servlet = new WebHookServlet(webHookDispatcher());
        final var bean = new ServletRegistrationBean<>(
                servlet, WEBHOOK_PATH + "/*"
        );
        bean.setLoadOnStartup(1);
        return bean;
    }

    private @NonNull URI formFullURI(@NonNull ExternalURI externalURI) {
        return externalURI.getURI().resolve(serverContextPath+WEBHOOK_PATH);
    }

}
