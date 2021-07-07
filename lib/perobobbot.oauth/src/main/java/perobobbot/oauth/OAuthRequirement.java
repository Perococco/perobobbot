package perobobbot.oauth;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.Optional;

@RequiredArgsConstructor
public class OAuthRequirement {

    public static @NonNull OAuthRequirement fromMethod(@NonNull Method method) {
        return new OAuthRequirement(method.getAnnotation(ClientOAuth.class),method.getAnnotation(UserOAuth.class));
    }


    private final ClientOAuth clientOAuth;
    private final UserOAuth userOAuth;

    public boolean hasNoRequirement() {
        return clientOAuth == null && userOAuth == null;
    }

    public @NonNull Optional<ClientOAuth> getClientOAuth() {
        return Optional.ofNullable(clientOAuth);
    }

    public @NonNull Optional<UserOAuth> getUserOAuth() {
        return Optional.ofNullable(userOAuth);
    }

    public boolean isClient() {
        return clientOAuth != null;
    }

    public boolean isUser() {
        return userOAuth != null;
    }
}
