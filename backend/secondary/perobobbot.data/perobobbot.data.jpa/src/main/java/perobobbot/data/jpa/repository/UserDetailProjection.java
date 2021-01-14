package perobobbot.data.jpa.repository;

public interface UserDetailProjection {

    String getLogin();
    String getPassword();
    boolean isDeactivated();
    String getLocale();
    String getJwtClaim();
    String getRoleKind();
    String getOperation();
}
