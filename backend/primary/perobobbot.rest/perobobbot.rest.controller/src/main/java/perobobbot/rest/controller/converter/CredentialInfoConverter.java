package perobobbot.rest.controller.converter;

import lombok.NonNull;
import perobobbot.data.com.DataCredentialInfo;
import perobobbot.lang.NoTypeScript;
import perobobbot.lang.Transformer;
import perobobbot.rest.com.RestCredentialInfo;

@NoTypeScript
public enum CredentialInfoConverter implements Transformer<DataCredentialInfo, RestCredentialInfo> {
    INSTANCE,
    ;

    @Override
    public @NonNull RestCredentialInfo transform(@NonNull DataCredentialInfo input) {
        return RestCredentialInfo.builder()
                                 .id(input.getId())
                                 .login(input.getOwnerLogin())
                                 .platform(input.getPlatform())
                                 .nick(input.getNick())
                                 .secretAvailable(input.hasSecret())
                                 .build();
    }
}
