package perobobbot.data.jpa.repository;

import lombok.NonNull;
import perobobbot.lang.IdentifiedEnumTools;
import perobobbot.lang.Platform;
import perobobbot.security.com.Identification;

public interface UserDetailProjection {

    String getLogin();
    String getOpenIdPlatform();
    String getPassword();
    boolean isDeactivated();
    String getLocale();
    String getJwtClaim();
    String getRoleKind();
    String getOperation();

    default @NonNull Identification identification() {
        if (getPassword() != null) {
            return Identification.password(getPassword());
        }
        return Identification.openId(IdentifiedEnumTools.getEnum(getOpenIdPlatform(), Platform.class));
    }
}
