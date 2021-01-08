package perobobbot.data.com;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class DuplicateRoleForUser extends DataException {

    private final @NonNull String login;
    private final @NonNull RoleKind roleKind;

    public DuplicateRoleForUser(@NonNull String login,@NonNull RoleKind roleKind) {
        super("The role '"+roleKind+"' is already assign to user '"+login+"'");
        this.roleKind = roleKind;
        this.login = login;
    }
}
