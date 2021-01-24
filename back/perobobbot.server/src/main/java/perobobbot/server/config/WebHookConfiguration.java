package perobobbot.server.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.http.WebHookDispatcher;
import perobobbot.lang.ThrowableTool;
import perobobbot.server.config.externaluri.ExternalURI;

import java.net.URI;

@Log4j2
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
        try {
            var webHookURI = formFullURI(webHookExternalURI);
            var oauthURI = formFullURI(oauthExternalURI);
            return WebHookDispatcher.create(webHookURI, oauthURI);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.error("WebHook are disabled. Check your configuration.");
            return WebHookDispatcher.disabled();
        }
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
