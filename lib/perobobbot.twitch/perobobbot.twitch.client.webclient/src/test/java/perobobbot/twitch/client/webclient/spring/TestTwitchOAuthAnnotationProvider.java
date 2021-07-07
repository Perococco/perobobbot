package perobobbot.twitch.client.webclient.spring;

import lombok.NonNull;
import lombok.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.lang.TokenType;
import perobobbot.oauth.RequiredToken;
import perobobbot.twitch.client.api.TwitchService;
import perobobbot.twitch.client.api.TwitchServiceEventSub;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TestTwitchOAuthAnnotationProvider {


    private static Stream<MethodInfo> methodInfoStream() {
        return Stream.of(
                MethodInfo.create(TwitchServiceEventSub::getEventSubSubscriptions, TokenType.CLIENT_TOKEN, null, null),
                MethodInfo.create(TwitchServiceEventSub::createEventSubSubscription, TokenType.CLIENT_TOKEN, null, null),
                MethodInfo.create(TwitchServiceEventSub::deleteEventSubSubscription, TokenType.CLIENT_TOKEN, null, null)
        );
    }

    ;


    private TwitchOAuthAnnotationProvider provider;

    private static Stream<Arguments> tokenSamples() {
        return methodInfoStream().map(m -> Arguments.of(m.method, m.tokenType));
    }

    @BeforeEach
    public void setUp() {
        this.provider = new TwitchOAuthAnnotationProvider();
    }

    @ParameterizedTest
    @MethodSource("tokenSamples")
    public void testAnnotation(@NonNull Method method, TokenType expected) {
        final var actual = provider.findRequiredToken(method).map(RequiredToken::value).orElse(null);
        Assertions.assertEquals(expected,actual);
    }

    @Value
    private static class MethodInfo {

        public static MethodInfo create(@NonNull Consumer<TwitchService> call, TokenType tokenType, String requiredScope, Boolean optional) {
            final var method = extractMethod(call);
            return new MethodInfo(method, tokenType, requiredScope, optional);
        }

        public static <T> MethodInfo create(@NonNull BiConsumer<TwitchService, T> call, TokenType tokenType, String requiredScope, Boolean optional) {
            final var method = extractMethod(t -> call.accept(t, null));
            return new MethodInfo(method, tokenType, requiredScope, optional);
        }

        @NonNull Method method;
        TokenType tokenType;
        String requiredScope;
        Boolean optional;

    }

    private static Method extractMethod(Consumer<TwitchService> call) {
        final AtomicReference<Method> _method = new AtomicReference<>(null);
        final TwitchService service = (TwitchService) Proxy.newProxyInstance(
                TwitchService.class.getClassLoader(),
                new Class<?>[]{TwitchService.class},
                (proxy, method, args) -> {
                    _method.set(method);
                    throw new RuntimeException("OUPS");
                });

        try {
            call.accept(service);
        } catch (RuntimeException r) {
            if (!r.getMessage().equals("OUPS")) {
                throw r;
            }
        }
        final var method = _method.get();
        if (method == null) {
            throw new RuntimeException("Could not find method");
        }
        return method;
    }
}
