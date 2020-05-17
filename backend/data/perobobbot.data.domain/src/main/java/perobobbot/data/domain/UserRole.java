package perobobbot.data.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author Perococco
 */
@Entity
@Getter
@Setter(AccessLevel.PROTECTED)
@Table(name = "USER_ROLE")
public class UserRole extends SimplePersistentObject {

    public static final String COLUMN_NAME__USER_ID = "USER_ID";
    public static final String COLUMN_NAME__ROLE_ID = "ROLE_ID";

    @ManyToOne
    @JoinColumn(name = COLUMN_NAME__USER_ID, foreignKey = @ForeignKey(name=("FK_USER_ROLE__USER")), insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = COLUMN_NAME__ROLE_ID, foreignKey = @ForeignKey(name=("FK_USER_ROLE__ROLE")), insertable = false, updatable = false)
    private Role role;

    protected UserRole() {
    }

    protected UserRole(@NonNull User user,@NonNull Role role) {
        this.user = user;
        this.role = role;
    }

    @NonNull
    public String getRoleName() {
        return role.getName();
    }
}
