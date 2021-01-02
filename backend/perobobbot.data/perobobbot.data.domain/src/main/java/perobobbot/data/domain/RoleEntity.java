package perobobbot.data.domain;

import com.google.common.collect.ImmutableSet;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import perobobbot.data.com.Role;
import perobobbot.lang.SetTool;
import perobobbot.lang.fp.Function1;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Perococco
 */
@Entity
@Table(name = "ROLE", uniqueConstraints = {@UniqueConstraint(name = "UK_ROLE__NAME",columnNames = {"NAME"})})
@Getter
@Setter(AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false,of = "name")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleEntity extends SimplePersistentObject {

    @NonNull
    @Column(name = "NAME")
    @NotBlank
    String name = "";

    @ManyToMany(cascade = CascadeType.PERSIST)
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "ROLE_OPERATION",
            joinColumns = {@JoinColumn(name = "ROLE_ID", foreignKey = @ForeignKey(name=("FK_ROLE_OPERATION__ROLE")))},
            inverseJoinColumns = {@JoinColumn(name = "OPERATION_ID", foreignKey = @ForeignKey(name=("FK_ROLE_OPERATION__OPERATION")))}
    )
    private final Set<OperationEntity> allowedOperations = new HashSet<>();

    public RoleEntity(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public <T> ImmutableSet<T> transformedAllowedOperations(Function1<? super OperationEntity, ? extends T> transformer) {
        return allowedOperations.stream().map(transformer).collect(SetTool.collector());
    }

    @NonNull
    public Stream<OperationEntity> allowedOperationStream() {
        return allowedOperations.stream();
    }

    @NonNull
    public RoleEntity allowOperation(@NonNull OperationEntity operation) {
        this.allowedOperations.add(operation);
        return this;
    }

    @NonNull
    public RoleEntity forbidOperation(@NonNull OperationEntity operation) {
        this.allowedOperations.remove(operation);
        return this;
    }

    private boolean isAllowed(@NonNull OperationEntity operation) {
        return allowedOperations.contains(operation);
    }

    public @NonNull Role toView() {
        return new Role(this.name,this.transformedAllowedOperations(OperationEntity::toView));
    }
}
