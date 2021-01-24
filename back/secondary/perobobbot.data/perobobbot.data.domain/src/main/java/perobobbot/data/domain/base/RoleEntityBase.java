package perobobbot.data.domain.base;

import lombok.*;
import org.hibernate.annotations.Type;
import perobobbot.data.domain.converter.OperationConverter;
import perobobbot.persistence.SimplePersistentObject;
import perobobbot.security.com.Operation;
import perobobbot.security.com.RoleKind;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Perococco
 */
@MappedSuperclass
@NoArgsConstructor
@Getter @Setter
@EqualsAndHashCode(callSuper = false,of = "role")
public class RoleEntityBase extends SimplePersistentObject {

    @NonNull
    @Column(name = "ROLE",unique = true)
    @Type(type = "perobobbot.persistence.type.IdentifiedEnumType")
    private RoleKind role = RoleKind.USER;

    @ElementCollection
    @CollectionTable(name = "ROLE_OPERATION",joinColumns = {@JoinColumn(name = "ROLE_ID")})
    @Column(name = "OPERATION")
    @Convert(converter = OperationConverter.class)
    private final Set<Operation> allowedOperations = new HashSet<>();

    public RoleEntityBase(@NonNull RoleKind role) {
        this.role = role;
    }


    @NonNull
    public Stream<Operation> allowedOperations() {
        return allowedOperations.stream();
    }

}
