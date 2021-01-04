package perobobbot.data.com;

import lombok.NonNull;
import perobobbot.lang.DTO;

@DTO
public enum RoleKind {
    ADMIN,
    USER,
    ;

    public @NonNull String getGrantedAuthorityName() {
        return "ROLE_"+name();
    }
}
