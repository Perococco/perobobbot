package perobobbot.server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import perobobbot.http.WebClientFactory;

import java.util.concurrent.Executors;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public ObjectMapper objectMapper() {
        return RestObjectMapper.create();
    }

    @Override
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(getTaskExecutor());
    }

    @Bean
    protected ConcurrentTaskExecutor getTaskExecutor() {
        return new ConcurrentTaskExecutor(
                Executors.newCachedThreadPool(new ThreadFactoryBuilder().setNameFormat("Async Executor %d").build()));
    }

    @Bean
    public @NonNull WebClientFactory webClientFactory(@NonNull ObjectMapper objectMapper) {
        final var exchangeStrategies = ExchangeStrategies.builder().codecs(configurer -> {
            configurer.customCodecs().register(new Jackson2JsonDecoder(objectMapper));
            configurer.customCodecs().register(new Jackson2JsonEncoder(objectMapper));
        }).build();
        return WebClientFactory.builder(WebClient.builder().exchangeStrategies(exchangeStrategies).build()).build();
    }


}
