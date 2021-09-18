package perobobbot.server.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.http.WebHookDispatcher;
import perobobbot.http.WebHookManager;
import perobobbot.lang.PluginService;
import perobobbot.lang.ThrowableTool;
import perobobbot.server.config.externaluri.ExternalURIProvider;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class WebHookConfiguration {

    public static final String WEBHOOK_PATH = "/webhook";

    @Qualifier("webhook")
    private final @NonNull ExternalURIProvider webHookExternalURIProvider;
    @Qualifier("oauth")
    private final @NonNull ExternalURIProvider oauthExternalURIProvider;

    @Bean
    @PluginService(type = WebHookManager.class, apiVersion = WebHookManager.VERSION, sensitive = false)
    public @NonNull WebHookDispatcher webHookDispatcher() {
        try {
            var webHookURI = webHookExternalURIProvider.get().addPrefix(WEBHOOK_PATH);
            var oauthURI = oauthExternalURIProvider.get().addPrefix(WEBHOOK_PATH);
            return WebHookDispatcher.create(webHookURI, oauthURI);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            LOG.warn("WebHooks are disabled. Check your configuration.");
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

}
