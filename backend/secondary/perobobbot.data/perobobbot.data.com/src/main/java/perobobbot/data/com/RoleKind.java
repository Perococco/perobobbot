package perobobbot.data.com;

import lombok.NonNull;
import perobobbot.lang.DTO;
import perobobbot.lang.IdentifiedEnum;

@DTO
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
