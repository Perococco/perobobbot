package perobobbot.data.domain;

import com.google.common.collect.ImmutableSet;
import lombok.*;
import org.hibernate.annotations.Type;
import perobobbot.data.domain.converter.OperationConverter;
import perobobbot.persistence.SimplePersistentObject;
import perobobbot.security.com.Operation;
import perobobbot.security.com.Role;
import perobobbot.security.com.RoleKind;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author perococco
 */
@Entity
@Table(name = "ROLE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter(AccessLevel.PROTECTED)
public class RoleEntity extends SimplePersistentObject {

    @NonNull
    @Column(name = "ROLE",unique = true)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private RoleKind role = RoleKind.USER;

    @ElementCollection
    @CollectionTable(name = "ROLE_OPERATION",joinColumns = {@JoinColumn(name = "ROLE_ID")})
    @Column(name = "OPERATION")
    @Convert(converter = OperationConverter.class)
    private final Set<Operation> allowedOperations = new HashSet<>();

    public RoleEntity(@NonNull RoleKind role) {
        this.role = role;
    }

    @NonNull
    public RoleEntity allowOperation(@NonNull Operation operation) {
        this.allowedOperations.add(operation);
        return this;
    }

    @NonNull
    public RoleEntity forbidOperation(@NonNull Operation operation) {
        this.allowedOperations.remove(operation);
        return this;
    }

    private boolean isAllowed(@NonNull Operation operation) {
        return allowedOperations.contains(operation);
    }

    public @NonNull Role toView() {
        return new Role(this.role, ImmutableSet.copyOf(allowedOperations));
    }

    @NonNull
    public Stream<Operation> allowedOperations() {
        return allowedOperations.stream();
    }

}
