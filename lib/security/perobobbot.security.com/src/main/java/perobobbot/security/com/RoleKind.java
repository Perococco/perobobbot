package perobobbot.security.com;

import lombok.NonNull;
import perobobbot.lang.IdentifiedEnum;
import perobobbot.lang.TypeScript;

@TypeScript
public enum RoleKind implements IdentifiedEnum {
    ADMIN,
    USER,
    ;

    public @NonNull String getGrantedAuthorityName() {
        return "ROLE_"+name();
    }


    @Override
    public @NonNull String getIdentification() {
        return name();
    }
}
