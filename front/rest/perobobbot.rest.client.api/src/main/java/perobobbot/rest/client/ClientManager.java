package perobobbot.rest.client;

import lombok.NonNull;
import perobobbot.lang.ServiceLoaderHelper;
import perobobbot.security.com.Credential;
import perobobbot.security.com.SimpleUser;

import java.util.ServiceLoader;
import java.util.concurrent.CompletionStage;

public interface ClientManager {

    @NonNull CompletionStage<SimpleUser> login(@NonNull Credential credential);

    void logout();

    @NonNull BotClient botClient();
    @NonNull CredentialClient credentialClient();
    @NonNull ExtensionClient extensionClient();
    @NonNull I18nClient i18nClient();
    @NonNull SecurityClient securityClient();
    @NonNull UserClient userClient();

}
