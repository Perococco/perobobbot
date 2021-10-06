package perobobbot.server.plugin;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import perobobbot.security.com.Identification;
import perobobbot.security.com.Operation;
import perobobbot.security.com.RoleKind;
import perobobbot.security.com.User;
import perobobbot.server.config.security.jwt.JwtAuthentication;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Locale;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PluginServiceHandler implements InvocationHandler {

    public static Object wrap(Object service, Class<?> type) {
        if (!type.isInterface()) {
            return service;
        }
        return type.cast(Proxy.newProxyInstance(service.getClass().getClassLoader(),new Class[]{type},new PluginServiceHandler(service)));
    }

    private final @NonNull Object delegate;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final var context = SecurityContextHolder.getContext();
        final var authentication = context.getAuthentication();
        if (authentication == null) {
            context.setAuthentication(AUTHENTICATION);
        }
        try {
            return method.invoke(delegate, args);
        } finally {
            if (authentication == null) {
                SecurityContextHolder.clearContext();
            } else {
                context.setAuthentication(authentication);
            }
        }
    }

    private static final User PLUGIN_USER = User.builder()
                                                .login("plugin")
                                                .jwtClaim("")
                                                .deactivated(false)
                                                .locale(Locale.ENGLISH)
                                                .identification(Identification.password("plugin"))
                                                .role(RoleKind.ADMIN)
                                                .operation(Operation.READ_CREDENTIALS)
                                                .build();

    private static final Authentication AUTHENTICATION = JwtAuthentication.create(PLUGIN_USER);
}
