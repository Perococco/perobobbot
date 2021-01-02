package perobobbot.data.domain;

import com.google.common.collect.ImmutableSet;
import lombok.*;
import perobobbot.data.com.Operation;
import perobobbot.data.com.Role;
import perobobbot.data.com.RoleKind;
import perobobbot.lang.SetTool;
import perobobbot.lang.fp.Function1;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Perococco
 */
@Entity
@Table(name = "ROLE", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})})
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false,of = "role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleEntity extends SimplePersistentObject {

    @NonNull
    @Column(name = "NAME")
    @Enumerated(EnumType.STRING)
    private RoleKind role = RoleKind.USER;

    @ElementCollection
    @CollectionTable(name = "ROLE_OPERATION",joinColumns = {@JoinColumn(name = "ROLE_ID")})
    @Column(name = "OPERATION")
    private final Set<Operation> allowedOperations = new HashSet<>();

    public RoleEntity(@NonNull RoleKind role) {
        this.role = role;
    }

    @NonNull
    public <T> ImmutableSet<T> transformedAllowedOperations(Function1<? super Operation, ? extends T> transformer) {
        return allowedOperations.stream().map(transformer).collect(SetTool.collector());
    }

    @NonNull
    public Stream<Operation> allowedOperationStream() {
        return allowedOperations.stream();
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
}
