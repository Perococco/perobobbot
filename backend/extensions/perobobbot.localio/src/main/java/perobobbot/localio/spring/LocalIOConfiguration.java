package perobobbot.localio.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.lang.ApplicationCloser;
import perobobbot.lang.StandardInputProvider;
import perobobbot.localio.LocalChatPlatform;

@Configuration
@RequiredArgsConstructor
public class LocalIOConfiguration {

    private final @NonNull ApplicationCloser applicationCloser;

    private final @NonNull StandardInputProvider standardInputProvider;

    @Bean(destroyMethod = "dispose")
    public LocalChatPlatform console() {
        return new LocalChatPlatform(applicationCloser,standardInputProvider);
    }

}
