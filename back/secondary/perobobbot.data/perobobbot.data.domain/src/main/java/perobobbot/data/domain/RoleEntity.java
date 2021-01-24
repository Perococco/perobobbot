package perobobbot.data.domain;

import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import perobobbot.data.domain.base.RoleEntityBase;
import perobobbot.security.com.Operation;
import perobobbot.security.com.Role;
import perobobbot.security.com.RoleKind;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Perococco
 */
@Entity
@Table(name = "ROLE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleEntity extends RoleEntityBase {

    public RoleEntity(@NonNull RoleKind role) {
        super(role);
    }

    @NonNull
    public RoleEntity allowOperation(@NonNull Operation operation) {
        this.getAllowedOperations().add(operation);
        return this;
    }

    @NonNull
    public RoleEntity forbidOperation(@NonNull Operation operation) {
        this.getAllowedOperations().remove(operation);
        return this;
    }

    private boolean isAllowed(@NonNull Operation operation) {
        return getAllowedOperations().contains(operation);
    }

    public @NonNull Role toView() {
        return new Role(this.getRole(), ImmutableSet.copyOf(getAllowedOperations()));
    }
}
