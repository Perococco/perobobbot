package perobobbot.oauth.tools;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import perobobbot.lang.fp.Consumer1;
import perobobbot.oauth.OAuthContextHolder;

@RequiredArgsConstructor
public class OAuthWebClientFactory implements Consumer1<WebClient.Builder> {

    @Override
    public void f(WebClient.@NonNull Builder builder) {
        OAuthContextHolder.getContext()
                          .getHeaderValues()
                          .forEach(builder::defaultHeader);
    }

}
