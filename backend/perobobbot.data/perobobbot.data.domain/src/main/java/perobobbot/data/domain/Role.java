package perobobbot.data.domain;

import com.google.common.collect.ImmutableList;
import lombok.*;
import perobobbot.lang.ListTool;
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
public class Role extends SimplePersistentObject {

    @NonNull
    @Column(name = "NAME")
    @NotBlank
    String name = "";

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "ROLE_OPERATION",
            joinColumns = {@JoinColumn(name = "ROLE_ID", foreignKey = @ForeignKey(name=("FK_ROLE_OPERATION__ROLE")))},
            inverseJoinColumns = {@JoinColumn(name = "OPERATION_ID", foreignKey = @ForeignKey(name=("FK_ROLE_OPERATION__OPERATION")))}
    )
    private final Set<Operation> allowedOperations = new HashSet<>();

    public Role(@NonNull String name) {
        this.name = name;
    }


    @NonNull
    public <T> ImmutableList<T> transformedAllowedOperations(Function1<? super Operation, ? extends T> transformer) {
        return allowedOperations.stream().map(transformer).collect(ListTool.collector());
    }

    @NonNull
    public Stream<Operation> allowedOperationStream() {
        return allowedOperations.stream();
    }

    @NonNull
    public Role allowOperation(@NonNull Operation operation) {
        this.allowedOperations.add(operation);
        return this;
    }

    @NonNull
    public Role forbidOperation(@NonNull Operation operation) {
        this.allowedOperations.remove(operation);
        return this;
    }

    private boolean isAllowed(@NonNull Operation operation) {
        return allowedOperations.contains(operation);
    }

}
