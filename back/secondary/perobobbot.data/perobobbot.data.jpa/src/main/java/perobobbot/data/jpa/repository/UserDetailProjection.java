package perobobbot.data.jpa.repository;

import lombok.NonNull;
import perobobbot.lang.IdentifiedEnumTools;
import perobobbot.lang.Platform;
import perobobbot.security.com.Authentication;

public interface UserDetailProjection {

    String getLogin();
    String getOpenIdPlatform();
    String getPassword();
    boolean isDeactivated();
    String getLocale();
    String getJwtClaim();
    String getRoleKind();
    String getOperation();

    default @NonNull Authentication identification() {
        if (getPassword() != null) {
            return Authentication.password(getPassword());
        }
        return Authentication.openId(IdentifiedEnumTools.getEnum(getOpenIdPlatform(), Platform.class));
    }
}
